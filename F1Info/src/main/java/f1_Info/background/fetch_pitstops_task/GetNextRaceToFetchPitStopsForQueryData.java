package f1_Info.background.fetch_pitstops_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceToFetchPitStopsForQueryData implements IQueryData<PitStopFetchInformationRecord> {
    int mFirstSeasonWithPistopData;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_race_to_fetch_pitstops_for";
    }
}
