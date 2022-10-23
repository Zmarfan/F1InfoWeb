package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetNextRaceInfoCommand implements Command {
    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(new NextRaceInfoResponse(
            SessionType.QUALIFYING.getKey(),
            LocalDateTime.now().plusDays(2).toLocalDate().atStartOfDay().toString(),
            false
        ));
    }
}
