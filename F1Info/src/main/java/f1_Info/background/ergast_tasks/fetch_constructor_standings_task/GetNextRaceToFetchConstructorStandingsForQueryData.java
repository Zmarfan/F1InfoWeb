package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceToFetchConstructorStandingsForQueryData implements IQueryData<RaceRecord> {
    int mFirstFormula1Season;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_race_to_fetch_constructor_standings_for";
    }
}
