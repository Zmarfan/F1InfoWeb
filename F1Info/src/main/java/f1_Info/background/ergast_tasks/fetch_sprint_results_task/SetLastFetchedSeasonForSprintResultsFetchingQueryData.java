package f1_Info.background.ergast_tasks.fetch_sprint_results_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedSeasonForSprintResultsFetchingQueryData implements IQueryData<Void> {
    int mLastFetchedSeason;

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_season_for_sprint_results_fetching";
    }
}
