package f1_Info.entry_points.reports.commands.get_all_driver_report_command;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetAllDriverReportCommand implements Command {
    private final int mSeason;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format("Fetch all driver report with parameters: season: %d, sortDirection: %s, sortColumn: %s", mSeason, mSortDirection, mSortColumn);
    }
    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok(mDatabase.getReportRows(mSeason, mSortDirection, mSortColumn).stream().map(AllDriverReportResponse::new).toList());
    }
}
