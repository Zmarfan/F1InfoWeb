package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetDriverReportFilterValuesCommand implements Command {
    private final int mSeason;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format("Fetch drivers from season %d", mSeason);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        final List<DriverEntry> driverEntries= mDatabase.getDriversFromSeason(mSeason).stream().map(DriverEntry::new).toList();
        final int roundsInSeason = mDatabase.getAmountOfRoundsInSeason(mSeason);
        return ok(new DriverReportFilterResponse(driverEntries, roundsInSeason));
    }
}
