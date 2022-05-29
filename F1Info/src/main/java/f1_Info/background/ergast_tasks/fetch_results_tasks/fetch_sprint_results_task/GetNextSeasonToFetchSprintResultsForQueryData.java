package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_sprint_results_task;

import database.IQueryData;
import lombok.Value;

@Value
public class GetNextSeasonToFetchSprintResultsForQueryData implements IQueryData<Integer> {
    int mFirstSeasonWithData;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_season_to_fetch_sprint_results_for";
    }
}
