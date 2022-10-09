package f1_Info.entry_points.user.commands.profile_picture_commands.update_user_profile_picture_command;

import f1_Info.entry_points.helper.Command;
import f1_Info.entry_points.user.commands.profile_picture_commands.Database;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.internalServerError;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UpdateUserProfilePictureCommand implements Command {
    private final long mUserId;
    private final MultipartFile mFile;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        try {
            mDatabase.updateUserProfilePicture(mUserId, mFile.getInputStream());
        } catch (final IOException e) {
            return internalServerError();
        }

        return ok();
    }
}