package f1_Info.entry_points.user.commands.profile_picture_commands.get_user_profile_picture_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetUserProfilePictureQueryData implements IQueryData<byte[]> {
    long m0UserId;

    @Override
    public String getStoredProcedureName() {
        return "get_user_profile_picture";
    }
}