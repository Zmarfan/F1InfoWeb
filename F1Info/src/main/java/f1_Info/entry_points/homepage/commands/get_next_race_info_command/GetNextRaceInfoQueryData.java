package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetNextRaceInfoQueryData implements IQueryData<NextRaceInfoRecord> {

    @Override
    public String getStoredProcedureName() {
        return "get_next_race_info";
    }
}