package f1_Info.entry_points.reports.commands.get_race_report_commands.overview;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_race_report_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetRaceOverviewReportCommand implements Command {
    private final int mSeason;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format("Fetch overview race report: season: %s, direction: %s, sortColumn: %s", mSeason, mSortDirection.getDirection(), mSortColumn);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok(mDatabase.getOverviewReportRows(mSeason, mSortDirection, mSortColumn).stream().map(OverviewRaceReportResponse::new).toList());
    }
}
