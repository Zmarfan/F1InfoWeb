package f1_Info.entry_points.user.commands.get_user_bell_notifications_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetUserBellNotificationsCommand implements Command {
    private final long mUserId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(mDatabase.getBellNotificationsToDisplay(mUserId).stream().map(BellNotificationResponse::new).toList());
    }
}
