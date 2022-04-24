package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class GetNextSeasonToFetchRaceResultsForQueryData implements IQueryData<Integer> {
    int mFirstSeasonWithData;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_season_to_fetch_race_results_for";
    }
}
