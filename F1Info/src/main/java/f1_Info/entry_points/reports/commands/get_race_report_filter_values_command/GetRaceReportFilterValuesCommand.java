package f1_Info.entry_points.reports.commands.get_race_report_filter_values_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetRaceReportFilterValuesCommand implements Command {
    private final int mSeason;
    private final Database mDatabase;

    @Override
    public String getAction() {
        return String.format("Fetch race report filter values from season %d", mSeason);
    }

    @Override
    public ResponseEntity<?> execute() throws Exception {
        final List<RaceData> races = mDatabase.getRacesInfo(mSeason).stream().map(RaceData::new).toList();

        return ok(new RaceReportFilterResponse(races));
    }
}
