package f1_Info.entry_points.friends.command.send_friend_request_command;

import f1_Info.entry_points.friends.FriendCodeHandler;
import f1_Info.entry_points.helper.Command;
import f1_Info.services.bell_notification_send_out_service.BellNotificationIcon;
import f1_Info.services.bell_notification_send_out_service.BellNotificationSendOutService;
import f1_Info.services.bell_notification_send_out_service.CreateNotificationParameters;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class SendFriendRequestCommand implements Command {
    private final String mFriendCode;
    private final long mUserId;
    private final FriendCodeHandler mFriendCodeHandler;
    private final Database mDatabase;
    private final BellNotificationSendOutService mNotificationSendOutService;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final Optional<Long> friendUserId = mFriendCodeHandler.userIdFromFriendCode(mFriendCode);
        if (friendUserId.isEmpty() || friendUserId.get() == mUserId) {
            return badRequest();
        }
        final SendFriendRequestRecord requestRecord = mDatabase.getSendFriendRequestInfo(mUserId, friendUserId.get());
        if (!requestRecord.getCanSendFriendRequest()) {
            return badRequest();
        }
        mDatabase.sendFriendRequest(mUserId, friendUserId.get());

        if (!requestRecord.getReceiverHasBlockedUser()) {
            mNotificationSendOutService.sendBellNotification(new CreateNotificationParameters(
                friendUserId.get(),
                BellNotificationIcon.HAPPY_SMILEY,
                "bellMessages.receivedFriendRequest",
                Map.of("user", requestRecord.getDisplayName())
            ));
        }

        return ok();
    }
}
