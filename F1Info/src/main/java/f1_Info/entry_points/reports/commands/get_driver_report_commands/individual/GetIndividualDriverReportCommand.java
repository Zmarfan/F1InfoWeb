package f1_Info.entry_points.reports.commands.get_driver_report_commands.individual;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetIndividualDriverReportCommand implements Command {
    private final int mSeason;
    private final String mDriverIdentifier;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format(
            "Fetch individual driver report with parameters: season: %d, driverId: %s, sortDirection: %s, sortColumn: %s",
            mSeason,
            mDriverIdentifier,
            mSortDirection,
            mSortColumn
        );
    }
    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok(
            mDatabase.getIndividualReportRows(mSeason, mDriverIdentifier, mSortDirection, mSortColumn)
                .stream()
                .map(IndividualDriverReportResponse::new)
                .toList()
        );
    }
}