package f1_Info.entry_points.user.commands.get_user_bell_notifications_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
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
}
