package f1_Info.entry_points.user.commands.profile_picture_commands.get_user_profile_picture_command;

import common.logger.Logger;
import f1_Info.entry_points.user.commands.profile_picture_commands.Database;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserProfilePictureCommand {
    private final long mUserId;
    private final Database mDatabase;
    private final Logger mLogger;

    public byte[] execute() {
        try {
            return mDatabase.getUserProfilePicture(mUserId);
        } catch (final Exception e) {
            mLogger.severe("execute", this.getClass(), String.format("Unable to fetch profile picture for user: %d", mUserId), e);
            return new byte[0];
        }
    }
}