package f1_Info.entry_points.user.commands.profile_picture_commands.update_user_profile_picture_command;

import database.IQueryData;
import lombok.Value;

import java.io.InputStream;

@Value
public class UploadUserProfilePictureQueryData implements IQueryData<Void> {
    long m0UserId;
    InputStream m1ImageInputStream;

    @Override
    public String getStoredProcedureName(){
        return"upload_user_profile_picture";
    }
}