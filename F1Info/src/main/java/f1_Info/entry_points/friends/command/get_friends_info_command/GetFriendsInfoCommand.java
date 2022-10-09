package f1_Info.entry_points.friends.command.get_friends_info_command;

import f1_Info.entry_points.friends.FriendCodeHandler;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetFriendsInfoCommand implements Command {
    private final long mUserId;
    private final Database mDatabase;
    private final FriendCodeHandler mFriendCodeHandler;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(new FriendsInfoResponse(
            mFriendCodeHandler.friendCodeFromUserId(mUserId),
            mDatabase.getFriendRequests(mUserId)
                .stream()
                .map(request -> new FriendInfo(request.getUserId(), request.getDisplayName(), mFriendCodeHandler.friendCodeFromUserId(request.getUserId())))
                .toList(),
            mDatabase.getFriends(mUserId)
                .stream()
                .map(friend -> new FriendInfo(friend.getUserId(), friend.getDisplayName(), mFriendCodeHandler.friendCodeFromUserId(friend.getUserId())))
                .toList()
        ));
    }
}
