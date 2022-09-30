package f1_Info.entry_points.friends.command.send_friend_request_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "SendFriendRequestCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void sendFriendRequest(final long userId, final long sendRequestUserId) throws SQLException {
        executeVoidQuery(new SendFriendRequestQueryData(userId, sendRequestUserId));
    }

    public SendFriendRequestRecord getSendFriendRequestInfo(final long userId, final long sendRequestUserId) throws SQLException {
        return executeQuery(new SendFriendRequestInfoQueryData(userId, sendRequestUserId));
    }
}