package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import common.constants.Country;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetNextRaceInfoCommand implements Command {
    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(new NextRaceInfoResponse(
            15,
            Country.UNITED_STATES.getCode(),
            "United States Grand Prix",
            List.of(
                new SessionInfo(SessionType.FIRST_PRACTICE, "2022-10-26T13:00", "2022-10-26T14:00", "2022-10-26T15:00"),
                new SessionInfo(SessionType.SECOND_PRACTICE, "2022-10-26T16:30", "2022-10-26T17:30", "2022-10-26T18:30"),
                new SessionInfo(SessionType.THIRD_PRACTICE, "2022-10-27T13:00", "2022-10-27T14:00", "2022-10-27T15:00"),
                new SessionInfo(SessionType.QUALIFYING, "2022-10-27T17:00", "2022-10-27T19:00", "2022-10-27T19:00"),
                new SessionInfo(SessionType.RACE, "2022-10-28T15:00", "2022-10-28T18:00", "2022-10-28T17:00")
            )
        ));
    }
}
