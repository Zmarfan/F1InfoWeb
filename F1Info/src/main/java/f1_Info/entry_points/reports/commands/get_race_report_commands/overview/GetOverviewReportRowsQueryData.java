package f1_Info.entry_points.reports.commands.get_race_report_commands.overview;

import database.IQueryData;
import lombok.Value;

@Value
public class GetOverviewReportRowsQueryData implements IQueryData<RaceOverviewRecord> {
    int m0Season;
    String m1ResultType;
    String m2SortDirection;
    String m3SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_race_report_overview";
    }
}
