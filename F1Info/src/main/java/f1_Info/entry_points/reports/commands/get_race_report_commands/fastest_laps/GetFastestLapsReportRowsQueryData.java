package f1_Info.entry_points.reports.commands.get_race_report_commands.fastest_laps;

import database.IQueryData;
import lombok.Value;

@Value
public class GetFastestLapsReportRowsQueryData implements IQueryData<FastestLapsRecord> {
    int m0Season;
    int m1Round;
    String m2ResultType;
    String m3SortDirection;
    String m4SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_fastest_laps_report";
    }
}
