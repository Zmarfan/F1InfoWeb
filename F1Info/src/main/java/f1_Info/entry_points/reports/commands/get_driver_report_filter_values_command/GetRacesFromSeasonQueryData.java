package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetRacesFromSeasonQueryData implements IQueryData<RaceFromSeasonRecord> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_report_filter_values_get_races_in_season";
    }
}
