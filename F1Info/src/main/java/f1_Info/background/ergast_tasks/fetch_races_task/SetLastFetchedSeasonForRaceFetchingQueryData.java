package f1_Info.background.ergast_tasks.fetch_races_task;

import database.IQueryData;
import lombok.Value;

@Value
public class SetLastFetchedSeasonForRaceFetchingQueryData implements IQueryData<Void> {
    int mLastFetchedSeason;

    @Override
    public String getStoredProcedureName() {
        return "tasks_set_last_fetched_season_for_race_fetching";
    }
}
