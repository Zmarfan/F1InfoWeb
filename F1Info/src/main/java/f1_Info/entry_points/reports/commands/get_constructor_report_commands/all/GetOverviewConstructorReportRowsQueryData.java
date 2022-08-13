package f1_Info.entry_points.reports.commands.get_constructor_report_commands.all;

import database.IQueryData;
import lombok.Value;

@Value
public class GetOverviewConstructorReportRowsQueryData implements IQueryData<OverviewConstructorReportRecord> {
    int m0Season;
    int m1Round;
    String m2SortDirection;
    String m3SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_overview_constructor_report_get_rows";
    }
}
