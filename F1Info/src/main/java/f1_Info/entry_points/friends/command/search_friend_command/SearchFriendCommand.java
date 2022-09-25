package f1_Info.entry_points.friends.command.search_friend_command;

import f1_Info.entry_points.friends.FriendCodeHandler;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Optional;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class SearchFriendCommand implements Command {
    private final String mFriendCode;
    private final long mUserId;
    private final Database mDatabase;
    private final FriendCodeHandler mFriendCodeHandler;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final Optional<Long> friendUserId = mFriendCodeHandler.userIdFromFriendCode(mFriendCode);
        if (friendUserId.isEmpty() || friendUserId.get() == mUserId) {
            return ok();
        }

        final SearchFriendRecord searchInfo = mDatabase.getSearchFriendInfo(mUserId, friendUserId.get());

        return ok(new SearchFriendResponse(
            mFriendCode,
            searchInfo.getDisplayName(),
            searchInfo.getFriendsInCommon(),
            searchInfo.getFriendStatus()
        ));
    }
}
