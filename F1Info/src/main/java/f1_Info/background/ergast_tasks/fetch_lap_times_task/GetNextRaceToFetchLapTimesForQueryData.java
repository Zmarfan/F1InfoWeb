package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceToFetchLapTimesForQueryData implements IQueryData<LapTimesFetchInformationRecord> {
    int mFirstSeasonWithLapTimeData;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_race_to_fetch_lap_times_for";
    }
}
