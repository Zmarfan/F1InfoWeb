package f1_Info.entry_points.reports.commands.get_race_report_commands.overview;

import common.constants.f1.ResultType;
import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_race_report_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetRaceOverviewReportCommand implements Command {
    private final int mSeason;
    private final ResultType mResultType;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(mDatabase.getOverviewReportRows(mSeason, mResultType, mSortDirection, mSortColumn).stream().map(OverviewRaceReportResponse::new).toList());
    }
}
