package f1_Info.background.fetch_lap_times_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.background.ergast.responses.lap_times.LapTimeData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchLapTimesTaskDatabase")
public class Database extends TaskDatabase {
    private static final int FIRST_SEASON_WITH_LAP_TIME_DATA = 1996;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<LapTimesFetchInformationRecord> getNextSeasonAndRoundToFetchLapTimesFor() throws SQLException {
        return executeOptionalQuery(new GetNextRaceToFetchLapTimesForQueryData(FIRST_SEASON_WITH_LAP_TIME_DATA));
    }

    public void mergeIntoLapTimesData(final List<LapTimeData> lapTimeDataList, final LapTimesFetchInformationRecord fetchInformation) throws SQLException {
        final List<MergeIntoLapTimesQueryData> queryDataList = lapTimeDataList
            .stream()
            .map(lapData -> mapToMergeQueryData(lapData, fetchInformation))
            .flatMap(List::stream)
            .toList();
        executeBulkOfWork(new BulkOfWork(queryDataList));
    }

    public void setLastFetchedLapTimesForRace(final LapTimesFetchInformationRecord fetchInformation) throws SQLException {
        executeVoidQuery(new SetLastFetchedRaceLapTimesFetchingQueryData(fetchInformation));
    }

    private List<MergeIntoLapTimesQueryData> mapToMergeQueryData(
        final LapTimeData lapData,
        final LapTimesFetchInformationRecord fetchInformation
    ) {
        return lapData.getTimingData()
            .stream()
            .map(timingData -> new MergeIntoLapTimesQueryData(lapData.getNumber(), timingData, fetchInformation))
            .toList();
    }
}
