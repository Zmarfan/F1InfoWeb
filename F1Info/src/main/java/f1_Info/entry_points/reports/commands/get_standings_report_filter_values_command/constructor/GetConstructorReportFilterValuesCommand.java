package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.Database;
import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.RaceEntry;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetConstructorReportFilterValuesCommand implements Command {
    private final int mSeason;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final List<ConstructorEntry> constructorEntries = mDatabase.getConstructorsFromSeason(mSeason).stream().map(ConstructorEntry::new).toList();
        final List<RaceEntry> races = mDatabase.getConstructorRacesFromSeason(mSeason).stream().map(RaceEntry::new).toList();
        final boolean seasonHasSprints = mDatabase.getConstructorSeasonHasSprint(mSeason);

        return ok(new ConstructorReportFilterResponse(constructorEntries, races, seasonHasSprints));
    }
}
