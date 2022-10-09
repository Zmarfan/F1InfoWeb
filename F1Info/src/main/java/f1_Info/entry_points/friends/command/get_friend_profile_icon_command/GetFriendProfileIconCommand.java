package f1_Info.entry_points.friends.command.get_friend_profile_icon_command;

import common.logger.Logger;
import f1_Info.entry_points.friends.FriendCodeHandler;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetFriendProfileIconCommand {
    private final String mFriendCode;
    private final FriendCodeHandler mFriendCodeHandler;
    private final Database mDatabase;
    private final Logger mLogger;

    public byte[] execute() {
        try {
            final Optional<Long> userId = mFriendCodeHandler.userIdFromFriendCode(mFriendCode);
            if (userId.isEmpty()) {
                return new byte[0];
            }
            return mDatabase.getFriendProfileIcon(userId.get());
        } catch (final Exception e) {
            mLogger.severe("execute", this.getClass(), String.format("Unable to fetch profile picture for friend: %s", mFriendCode), e);
            return new byte[0];
        }
    }
}