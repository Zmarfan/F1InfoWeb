package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.configuration.Configuration;
import f1_Info.database.BulkOfWork;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimeData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_SEASON_WITH_LAP_TIME_DATA;

@Component(value = "FetchLapTimesTaskDatabase")
public class Database extends TaskDatabase {
    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RaceRecord> getNextRaceToFetchLapTimesFor() throws SQLException {
        return executeOptionalQuery(new GetNextRaceToFetchLapTimesForQueryData(FIRST_SEASON_WITH_LAP_TIME_DATA));
    }

    public void mergeIntoLapTimesData(final List<LapTimeData> lapTimeDataList, final RaceRecord raceRecord) throws SQLException {
        final List<MergeIntoLapTimesQueryData> queryDataList = lapTimeDataList
            .stream()
            .map(lapData -> mapToMergeQueryData(lapData, raceRecord))
            .flatMap(List::stream)
            .toList();
        executeBulkOfWork(new BulkOfWork(queryDataList));
    }

    public void setLastFetchedRaceInHistory(final RaceRecord raceRecord) throws SQLException {
        executeVoidQuery(new SetLastFetchedRaceLapTimesFetchingQueryData(raceRecord));
    }

    private List<MergeIntoLapTimesQueryData> mapToMergeQueryData(
        final LapTimeData lapData,
        final RaceRecord raceRecord
    ) {
        return lapData.getTimingData()
            .stream()
            .map(timingData -> new MergeIntoLapTimesQueryData(lapData.getNumber(), timingData, raceRecord))
            .toList();
    }
}
