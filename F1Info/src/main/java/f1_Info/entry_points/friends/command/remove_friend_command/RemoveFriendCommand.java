package f1_Info.entry_points.friends.command.remove_friend_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class RemoveFriendCommand implements Command {
    private final long mUserId;
    private final long mFriendUserId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        if (!mDatabase.canRemoveFriend(mUserId, mFriendUserId)) {
            return badRequest();
        }
        mDatabase.removeFriend(mUserId, mFriendUserId);
        return ok();
    }
}
