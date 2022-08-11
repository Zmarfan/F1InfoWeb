package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import database.IQueryData;
import lombok.Value;

@Value
public class GetQualifyingReportRowsQueryData implements IQueryData<QualifyingRecord> {
    int m0Season;
    int m1Round;
    String m2SortDirection;
    String m3SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_qualifying_report";
    }
}
