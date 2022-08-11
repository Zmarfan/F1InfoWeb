package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetDriverReportFilterValuesCommand implements Command {
    private final int mSeason;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final List<DriverEntry> driverEntries= mDatabase.getDriversFromSeason(mSeason).stream().map(DriverEntry::new).toList();
        final List<RaceEntry> races = mDatabase.getRacesFromSeason(mSeason).stream().map(RaceEntry::new).toList();
        final boolean seasonHasSprints = mDatabase.getSeasonHasSprint(mSeason);

        return ok(new DriverReportFilterResponse(driverEntries, races, seasonHasSprints));
    }
}
