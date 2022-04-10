package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceToFetchDriverStandingsForQueryData implements IQueryData<RaceRecord> {
    int mFirstFormula1Season;

    @Override
    public String getStoredProcedureName() {
        return "tasks_get_next_race_to_fetch_driver_standings_for";
    }
}
