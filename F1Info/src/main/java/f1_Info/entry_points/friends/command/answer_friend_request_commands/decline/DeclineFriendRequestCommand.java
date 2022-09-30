package f1_Info.entry_points.friends.command.answer_friend_request_commands.decline;

import f1_Info.entry_points.friends.command.answer_friend_request_commands.AnswerFriendRequestForUserRecord;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.Database;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class DeclineFriendRequestCommand implements Command {
    long mUserId;
    long mAnswerUserId;
    Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final AnswerFriendRequestForUserRecord answerRecord = mDatabase.getAnswerFriendRequestForUserInfo(mUserId, mAnswerUserId);
        if (!answerRecord.getCanAnswerFriendRequest()) {
            return badRequest();
        }

        mDatabase.declineFriendRequest(mUserId, mAnswerUserId);
        return ok();
    }
}
