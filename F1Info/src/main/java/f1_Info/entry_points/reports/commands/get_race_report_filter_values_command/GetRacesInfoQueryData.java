package f1_Info.entry_points.reports.commands.get_race_report_filter_values_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetRacesInfoQueryData implements IQueryData<RaceInfoRecord> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_race_report_filter_values_get_races_info";
    }
}
