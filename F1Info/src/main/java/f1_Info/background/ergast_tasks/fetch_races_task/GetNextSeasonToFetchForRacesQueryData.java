package f1_Info.background.ergast_tasks.fetch_races_task;

import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class GetNextSeasonToFetchForRacesQueryData implements IQueryData<Integer> {
    int mFirstFormula1Season;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_season_to_fetch_for_races";
    }
}
