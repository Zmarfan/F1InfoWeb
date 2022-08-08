package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetAllDriverReportCommand implements Command {
    private final int mSeason;
    private final int mRound;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format(
            "Fetch all driver report with parameters: season: %d, round: %d, sortDirection: %s, sortColumn: %s",
            mSeason,
            mRound,
            mSortDirection,
            mSortColumn
        );
    }
    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok(mDatabase.getAllReportRows(mSeason, mRound, mSortDirection, mSortColumn).stream().map(AllDriverReportResponse::new).toList());
    }
}
