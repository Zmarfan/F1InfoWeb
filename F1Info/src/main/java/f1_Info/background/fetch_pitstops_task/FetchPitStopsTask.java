package f1_Info.background.fetch_pitstops_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.Tasks;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.pit_stop.PitStopData;
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
        final Optional<PitStopFetchInformationRecord> fetchInformation = mDatabase.getNextSeasonAndRoundToFetchPitStopsFor();
        if (fetchInformation.isEmpty()) {
            return;
        }

        final List<PitStopData> pitStops = mErgastProxy.fetchPitStopsFromRoundAndSeason(fetchInformation.get());
        if (!pitStops.isEmpty()) {
            mergeIntoDatabase(pitStops, fetchInformation.get());
        }
    }

    @Override
    protected Tasks getTaskType() {
        return Tasks.FETCH_PIT_STOPS_TASK;
    }

    private void mergeIntoDatabase(final List<PitStopData> pitStops, final PitStopFetchInformationRecord fetchInformation) throws SQLException {
        try {
            mDatabase.mergeIntoPitStopsData(pitStops, fetchInformation);
            logMergeIntoDatabaseInfo(pitStops, fetchInformation);
            mDatabase.setLastFetchedPitstopsForRace(fetchInformation);
        } catch (final SQLException e) {
            throw new SQLException(String.format(
                "Unable to merge in a total of %d Pit Stop entries for season: %d, round: %d into the database. Pit Stops: %s",
                pitStops.size(),
                fetchInformation.getSeason(),
                fetchInformation.getRound(),
                ListUtils.listToString(pitStops, PitStopData::toString)
            ), e);
        }
    }

    private void logMergeIntoDatabaseInfo(final List<PitStopData> pitStops, final PitStopFetchInformationRecord fetchInformation) {
        mLogger.info(
            "mergeIntoDatabase",
            FetchPitStopsTask.class,
            String.format(
                "Fetched a total of %d pit stop entries from ergast and merged into database for season: %d, round: %d",
                pitStops.size(),
                fetchInformation.getSeason(),
                fetchInformation.getRound()
            )
        );
    }
}