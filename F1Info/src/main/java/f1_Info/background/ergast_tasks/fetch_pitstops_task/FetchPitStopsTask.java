package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FetchPitStopsTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchPitStopsTask(
        ErgastProxy ergastProxy,
        Database database,
        Logger logger
    ) {
        super(logger, database);
        mErgastProxy = ergastProxy;
        mDatabase = database;
    }

    @Override
    protected void runTask() throws SQLException {
        final Optional<RaceRecord> raceRecord = mDatabase.getNextRaceToFetchPitStopsFor();
        if (raceRecord.isEmpty()) {
            return;
        }

        final List<PitStopData> pitStops = mErgastProxy.fetchPitStopsForRace(raceRecord.get());
        if (!pitStops.isEmpty()) {
            mergeIntoDatabase(pitStops, raceRecord.get());
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_PIT_STOPS_TASK;
    }

    private void mergeIntoDatabase(final List<PitStopData> pitStops, final RaceRecord raceRecord) throws SQLException {
        try {
            mDatabase.mergeIntoPitStopsData(pitStops, raceRecord);
            logMergeIntoDatabaseInfo(pitStops, raceRecord);
            mDatabase.setLastFetchedPitstopsForRace(raceRecord);
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d Pit Stop entries for season: %d, round: %d into the database. Pit Stops: %s",
                pitStops.size(),
                raceRecord.getSeason(),
                raceRecord.getRound(),
                ListUtils.listToString(pitStops, PitStopData::toString)
            ), e);
        }
    }

    private void logMergeIntoDatabaseInfo(final List<PitStopData> pitStops, final RaceRecord raceRecord) {
        mLogger.info(
            "mergeIntoDatabase",
            FetchPitStopsTask.class,
            String.format(
                "Fetched a total of %d pit stop entries from ergast and merged into database for season: %d, round: %d",
                pitStops.size(),
                raceRecord.getSeason(),
                raceRecord.getRound()
            )
        );
    }
}