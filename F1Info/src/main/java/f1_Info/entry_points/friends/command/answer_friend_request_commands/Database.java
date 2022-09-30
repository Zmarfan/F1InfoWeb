package f1_Info.entry_points.friends.command.answer_friend_request_commands;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.accept.AcceptFriendRequestQueryData;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.decline.DeclineFriendRequestQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "AnswerFriendRequestCommandsDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public AnswerFriendRequestForUserRecord getAnswerFriendRequestForUserInfo(final long userId, final long answerUserId) throws SQLException {
        return executeQuery(new AnswerFriendRequestForUserInfoQueryData(userId, answerUserId));
    }

    public void acceptFriendRequest(final long userId, final long answerUserId) throws SQLException {
        executeVoidQuery(new AcceptFriendRequestQueryData(userId, answerUserId));
    }

    public void declineFriendRequest(final long userId, final long answerUserId) throws SQLException {
        executeVoidQuery(new DeclineFriendRequestQueryData(userId, answerUserId));
    }
}