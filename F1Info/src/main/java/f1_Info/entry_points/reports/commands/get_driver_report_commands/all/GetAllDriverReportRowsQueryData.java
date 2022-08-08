package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import database.IQueryData;
import lombok.Value;

@Value
public class GetAllDriverReportRowsQueryData implements IQueryData<AllDriverReportRecord> {
    int m0Season;
    String m1SortDirection;
    String m2SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_all_driver_report_get_rows";
    }
}
