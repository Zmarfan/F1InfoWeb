package f1_Info.entry_points.reports.commands.get_drivers_from_season_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class GetDriversFromSeasonCommand implements Command {
    private final int mSeason;

    @Override
    public String getAction() {
        return String.format("Fetch drivers from season %d", mSeason);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        return null;
    }
}
