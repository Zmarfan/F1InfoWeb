package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor;

import database.IQueryData;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.RaceFromSeasonRecord;
import lombok.Value;

@Value
public class GetConstructorRacesFromSeasonQueryData implements IQueryData<RaceFromSeasonRecord> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_constructor_report_filter_values_get_races_in_season";
    }
}
