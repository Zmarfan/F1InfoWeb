package f1_Info.entry_points.friends.command.remove_friend_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "RemoveFriendCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public boolean canRemoveFriend(final long userId, final long friendUserId) throws SQLException {
        return executeBasicQuery(new CanRemoveFriendQueryData(userId, friendUserId));
    }

    public void removeFriend(final long userId, final long friendUserId) throws SQLException {
        executeVoidQuery(new RemoveFriendQueryData(userId, friendUserId));
    }
}