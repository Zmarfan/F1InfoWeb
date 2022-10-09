package f1_Info.entry_points.user.commands.profile_picture_commands;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.user.commands.profile_picture_commands.get_user_profile_picture_command.GetUserProfilePictureQueryData;
import f1_Info.entry_points.user.commands.profile_picture_commands.update_user_profile_picture_command.UploadUserProfilePictureQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@Component(value = "ProfilePictureCommandsDatabase}")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void updateUserProfilePicture(final long userId, final InputStream imageInputStream) throws SQLException {
        executeVoidQuery(new UploadUserProfilePictureQueryData(userId, imageInputStream));
    }

    public byte[] getUserProfilePicture(final long userId) throws SQLException, IOException {
        final byte[] imageData = executeBasicQuery(new GetUserProfilePictureQueryData(userId));
        if (imageData == null) {
            return getClass().getResourceAsStream("/default_profile_picture.jpg").readAllBytes();
        }
        return imageData;
    }
}