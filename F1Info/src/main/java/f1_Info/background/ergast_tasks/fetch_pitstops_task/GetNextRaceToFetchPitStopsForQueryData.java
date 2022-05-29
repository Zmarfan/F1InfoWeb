package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceToFetchPitStopsForQueryData implements IQueryData<RaceRecord> {
    int mFirstSeasonWithPitstopData;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_race_to_fetch_pitstops_for";
    }
}
