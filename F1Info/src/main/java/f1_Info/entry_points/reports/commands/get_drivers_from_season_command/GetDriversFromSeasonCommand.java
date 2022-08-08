package f1_Info.entry_points.reports.commands.get_drivers_from_season_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetDriversFromSeasonCommand implements Command {
    private final int mSeason;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format("Fetch drivers from season %d", mSeason);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        return ok(mDatabase.getDriversFromSeason(mSeason).stream().map(DriverReportDriverResponse::new).toList());
    }
}
