package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task;

import database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedSeasonForRaceResultsFetchingQueryData implements IQueryData<Void> {
    int mLastFetchedSeason;

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_season_for_race_results_fetching";
    }
}
