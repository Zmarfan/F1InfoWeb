package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchPitStopsTaskDatabase")
public class Database extends TaskDatabase {
    private static final int FIRST_SEASON_WITH_PISTOP_DATA = 2011;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<PitStopFetchInformationRecord> getNextSeasonAndRoundToFetchPitStopsFor() throws SQLException {
        return executeOptionalQuery(new GetNextRaceToFetchPitStopsForQueryData(FIRST_SEASON_WITH_PISTOP_DATA));
    }

    public void mergeIntoPitStopsData(final List<PitStopData> pitStopDataList, final PitStopFetchInformationRecord fetchInformation) throws SQLException {
        executeBulkOfWork(new BulkOfWork(pitStopDataList.stream().map(pitStopData -> new MergeIntoPitStopsQueryData(pitStopData, fetchInformation)).toList()));
    }

    public void setLastFetchedPitstopsForRace(final PitStopFetchInformationRecord fetchInformationRecord) throws SQLException {
        executeVoidQuery(new SetLastFetchedRacePitStopFetchingQueryData(fetchInformationRecord));
    }
}