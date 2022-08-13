package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor;

import database.IQueryData;
import lombok.Value;

@Value
public class GetConstructorSeasonHasSprintQueryData implements IQueryData<Boolean> {
    int mSeason;

    @Override
    public String getStoredProcedureName() {
        return "get_constructor_report_filter_values_get_season_has_sprints";
    }
}
