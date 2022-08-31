package f1_Info.entry_points.user.commands.bell_notification_commands.mark_bell_notifications_as_opened_command;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.user.commands.bell_notification_commands.Database;
import f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command.BellNotificationRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class MarkBellNotificationsAsOpenedCommand implements Command {
    private final long mUserId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final List<Long> notificationsToMarkAsOpened = mDatabase.getBellNotificationsToDisplay(mUserId)
            .stream()
            .filter(notification -> !notification.getOpened())
            .map(BellNotificationRecord::getNotificationId).toList();
        mDatabase.markBellNotificationsAsOpened(mUserId, notificationsToMarkAsOpened);
        return ok();
    }
}
