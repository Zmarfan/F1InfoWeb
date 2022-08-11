package f1_Info.entry_points.reports.commands.get_race_report_commands.pit_stops;

import database.IQueryData;
import lombok.Value;

@Value
public class GetPitStopsReportRowsQueryData implements IQueryData<PitStopRecord> {
    int m0Season;
    int m1Round;
    String m2SortDirection;
    String m3SortColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_pit_stops_report";
    }
}
