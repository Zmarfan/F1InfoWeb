package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import database.IQueryData;
import lombok.Value;

@Value
public class GetIndividualDriverReportRowsQueryData implements IQueryData<IndividualDriverReportRecord> {
    int m0Season;
    String m1DriverIdentifier;
    String m2ResultType;
    String m3SortingDirection;
    String m4SortingColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_individual_driver_report_get_rows";
    }
}
