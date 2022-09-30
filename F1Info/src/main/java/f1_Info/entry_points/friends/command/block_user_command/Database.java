package f1_Info.entry_points.friends.command.block_user_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.friends.command.get_friends_info_command.GetFriendRequestsQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "BlockUserCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void blockUser(final long userId, final long blockUserId) throws SQLException {
        executeVoidQuery(new BlockUserQueryData(userId, blockUserId));
    }

    public boolean canBlockUser(final long userId, final long blockUserId) throws SQLException {
        return executeBasicQuery(new CanBlockUserQueryData(userId, blockUserId));
    }
}