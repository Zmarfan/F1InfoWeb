package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FetchDriverStandingsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchDriverStandingsTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(logger, database);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_DRIVER_STANDINGS_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final Optional<RaceRecord> raceRecord = mDatabase.getNextRaceToFetchDriverStandingsFor();
        if (raceRecord.isEmpty()) {
            return;
        }

        final List<DriverStandingsData> driverStandings = mErgastProxy.fetchDriverStandingsForRace(raceRecord.get());
        if (!driverStandings.isEmpty()) {
            mergeIntoDatabase(driverStandings, raceRecord.get());
        }
    }

    private void mergeIntoDatabase(final List<DriverStandingsData> driverStandings, final RaceRecord raceRecord) throws SQLException {
        try {
            mDatabase.mergeIntoPitStopsData(driverStandings, raceRecord);
            logMergeIntoDatabaseInfo(driverStandings, raceRecord);
            mDatabase.setLastFetchedRaceInHistory(raceRecord);
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d driver standing entries for season: %d, round: %d into the database. Driver Standings: %s",
                driverStandings.size(),
                raceRecord.getSeason(),
                raceRecord.getRound(),
                ListUtils.listToString(driverStandings, DriverStandingsData::toString)
            ), e);
        }
    }

    private void logMergeIntoDatabaseInfo(final List<DriverStandingsData> driverStandings, final RaceRecord raceRecord) {
        mLogger.info(
            "mergeIntoDatabase",
            FetchDriverStandingsTask.class,
            String.format(
                "Fetched a total of %d driver standing entries from ergast and merged into database for season: %d, round: %d",
                driverStandings.size(),
                raceRecord.getSeason(),
                raceRecord.getRound()
            )
        );
    }
}
