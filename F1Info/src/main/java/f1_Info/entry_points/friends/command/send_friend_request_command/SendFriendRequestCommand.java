package f1_Info.entry_points.friends.command.send_friend_request_command;

import f1_Info.entry_points.friends.FriendCodeHandler;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Optional;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class SendFriendRequestCommand implements Command {
    private final String mFriendCode;
    private final long mUserId;
    private final FriendCodeHandler mFriendCodeHandler;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final Optional<Long> friendUserId = mFriendCodeHandler.userIdFromFriendCode(mFriendCode);
        if (friendUserId.isEmpty() || friendUserId.get() == mUserId || !mDatabase.canSendFriendRequest(mUserId, friendUserId.get())) {
            return badRequest();
        }
        mDatabase.sendFriendRequest(mUserId, friendUserId.get());
        return ok();
    }
}
