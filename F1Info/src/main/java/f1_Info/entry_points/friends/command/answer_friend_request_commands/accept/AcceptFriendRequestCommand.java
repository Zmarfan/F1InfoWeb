package f1_Info.entry_points.friends.command.answer_friend_request_commands.accept;

import f1_Info.entry_points.friends.command.answer_friend_request_commands.AnswerFriendRequestForUserRecord;
import f1_Info.entry_points.friends.command.answer_friend_request_commands.Database;
import f1_Info.entry_points.helper.Command;
import f1_Info.services.bell_notification_send_out_service.BellNotificationIcon;
import f1_Info.services.bell_notification_send_out_service.BellNotificationSendOutService;
import f1_Info.services.bell_notification_send_out_service.CreateNotificationParameters;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Map;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class AcceptFriendRequestCommand implements Command {
    long mUserId;
    long mAnswerUserId;
    Database mDatabase;
    BellNotificationSendOutService mBellNotificationSendOutService;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final AnswerFriendRequestForUserRecord answerRecord = mDatabase.getAnswerFriendRequestForUserInfo(mUserId, mAnswerUserId);
        if (!answerRecord.getCanAnswerFriendRequest()) {
            return badRequest();
        }

        mDatabase.acceptFriendRequest(mUserId, mAnswerUserId);
        mBellNotificationSendOutService.sendBellNotification(new CreateNotificationParameters(
            mAnswerUserId,
            BellNotificationIcon.HAPPY_SMILEY,
            "bellMessages.acceptFriendRequest",
            Map.of("user", answerRecord.getDisplayName())
        ));

        return ok();
    }
}
