package f1_Info.entry_points.user.commands.bell_notification_commands;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command.BellNotificationRecord;
import f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command.GetBellNotificationsToDisplayQueryData;
import f1_Info.entry_points.user.commands.bell_notification_commands.mark_bell_notifications_as_opened_command.MarkBellNotificationsAsOpenedQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetUserBellNotificationsCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<BellNotificationRecord> getBellNotificationsToDisplay(final long userId) throws SQLException {
        return executeListQuery(new GetBellNotificationsToDisplayQueryData(userId));
    }

    public void markBellNotificationsAsOpened(final long userId, final List<Long> notificationIds) throws SQLException {
        executeBulkOfWork(new BulkOfWork(notificationIds.stream().map(id -> new MarkBellNotificationsAsOpenedQueryData(userId, id)).toList()));
    }
}
