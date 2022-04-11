package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FetchConstructorStandingsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchConstructorStandingsTask(
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
        return Tasks.FETCH_CONSTRUCTOR_STANDINGS_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final Optional<RaceRecord> raceRecord = mDatabase.getNextRaceToFetchConstructorStandingsFor();
        if (raceRecord.isEmpty()) {
            return;
        }

        final List<ConstructorStandingsData> constructorStandings = mErgastProxy.fetchConstructorStandingsForRace(raceRecord.get());
        if (!constructorStandings.isEmpty()) {
            mergeIntoDatabase(constructorStandings, raceRecord.get());
        }
    }

    private void mergeIntoDatabase(final List<ConstructorStandingsData> constructorStandings, final RaceRecord raceRecord) throws SQLException {
        try {
            mDatabase.mergeIntoConstructorStandingsData(constructorStandings, raceRecord);
            logMergeIntoDatabaseInfo(constructorStandings, raceRecord);
            mDatabase.setLastFetchedRaceInHistory(raceRecord);
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d constructor standing entries for season: %d, round: %d into the database. Constructor Standings: %s",
                constructorStandings.size(),
                raceRecord.getSeason(),
                raceRecord.getRound(),
                ListUtils.listToString(constructorStandings, ConstructorStandingsData::toString)
            ), e);
        }
    }

    private void logMergeIntoDatabaseInfo(final List<ConstructorStandingsData> constructorStandings, final RaceRecord raceRecord) {
        mLogger.info(
            "mergeIntoDatabase",
            FetchConstructorStandingsTask.class,
            String.format(
                "Fetched a total of %d constructor standing entries from ergast and merged into database for season: %d, round: %d",
                constructorStandings.size(),
                raceRecord.getSeason(),
                raceRecord.getRound()
            )
        );
    }
}
