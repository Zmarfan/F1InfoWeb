package f1_Info.entry_points.reports.commands.get_race_report_commands.race_result;

import database.IQueryData;
import lombok.Value;

@Value
public class GetRaceResultReportRowsQueryData implements IQueryData<RaceResultRecord> {
    int m0Season;
    int m1Round;
    String m2ResultType;
    String m3SortDirection;
    String m4SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_race_result_report";
    }
}
