package f1_Info.entry_points.friends.command.get_friends_info_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "GetFriendsInfoCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<FriendRequestRecord> getFriendRequests(final long userId) throws SQLException {
        return executeListQuery(new GetFriendRequestsQueryData(userId));
    }
}