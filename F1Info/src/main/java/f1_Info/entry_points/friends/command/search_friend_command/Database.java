package f1_Info.entry_points.friends.command.search_friend_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "SearchFriendCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public SearchFriendRecord getSearchFriendInfo(final long userId, final long searchUserId) throws SQLException {
        return executeQuery(new GetSearchFriendInfoQueryData(userId, searchUserId));
    }
}