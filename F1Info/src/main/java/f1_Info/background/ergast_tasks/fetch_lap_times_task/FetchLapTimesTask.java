package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimeData;
import f1_Info.logger.Logger;
import f1_Info.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FetchLapTimesTask extends TaskWrapper {
    private final ErgastProxy mErgastProxy;
    private final Database mDatabase;

    @Autowired
    public FetchLapTimesTask(
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
        return Tasks.FETCH_LAP_TIMES_TASK;
    }

    @Override
    protected void runTask() throws SQLException {
        final Optional<LapTimesFetchInformationRecord> fetchInformation = mDatabase.getNextSeasonAndRoundToFetchLapTimesFor();
        if (fetchInformation.isEmpty()) {
            return;
        }

        final List<LapTimeData> lapTimesPerLap = mErgastProxy.fetchLapTimesFromRoundAndSeason(fetchInformation.get());
        if (!lapTimesPerLap.isEmpty()) {
            mergeIntoDatabase(lapTimesPerLap, fetchInformation.get());
        }
    }

    private void mergeIntoDatabase(final List<LapTimeData> lapTimes, final LapTimesFetchInformationRecord fetchInformation) throws SQLException {
        try {
            mDatabase.mergeIntoLapTimesData(lapTimes, fetchInformation);
            logMergeIntoDatabaseInfo(lapTimes, fetchInformation);
            mDatabase.setLastFetchedLapTimesForRace(fetchInformation);
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d Lap entries containing laps for season: %d, round: %d into the database. Lap Times: %s",
                lapTimes.size(),
                fetchInformation.getSeason(),
                fetchInformation.getRound(),
                ListUtils.listToString(lapTimes, LapTimeData::toString)
            ), e);
        }
    }

    private void logMergeIntoDatabaseInfo(final List<LapTimeData> lapTimes, final LapTimesFetchInformationRecord fetchInformation) {
        mLogger.info(
            "mergeIntoDatabase",
            FetchLapTimesTask.class,
            String.format(
                "Fetched a total of %d lap entries with lap data containing %d laps from ergast and merged into database for season: %d, round: %d",
                lapTimes.size(),
                lapTimes.stream().map(LapTimeData::getTimingData).flatMap(List::stream).toList().size(),
                fetchInformation.getSeason(),
                fetchInformation.getRound()
            )
        );
    }
}
