package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.SortDirection;
import f1_Info.entry_points.reports.commands.get_race_report_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetQualifyingReportCommand implements Command {
    private final int mSeason;
    private final int mRound;
    private final SortDirection mSortDirection;
    private final String mSortColumn;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(
            mDatabase.getQualifyingReportRows(mSeason, mRound, mSortDirection, mSortColumn).stream().map(QualifyingReportResponse::new).toList()
        );
    }
}
