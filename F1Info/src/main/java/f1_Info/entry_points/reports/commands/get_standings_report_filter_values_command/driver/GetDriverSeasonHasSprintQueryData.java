package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.driver;

import database.IQueryData;
import lombok.Value;

@Value
public class GetDriverSeasonHasSprintQueryData implements IQueryData<Boolean> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_driver_report_filter_values_get_season_has_sprints";
    }
}
