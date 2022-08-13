package f1_Info.entry_points.reports.commands.get_constructor_report_commands.individual;

import database.IQueryData;
import lombok.Value;

@Value
public class GetIndividualConstructorReportRowsQueryData implements IQueryData<IndividualConstructorReportRecord> {
    int m0Season;
    String m1ConstructorIdentifier;
    String m2ResultType;
    String m3SortingDirection;
    String m4SortingColumn;

    @Override
    public String getStoredProcedureName() {
        return "get_individual_constructor_report_get_rows";
    }
}
