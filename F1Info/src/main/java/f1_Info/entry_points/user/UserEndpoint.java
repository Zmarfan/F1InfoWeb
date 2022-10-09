package f1_Info.entry_points.user;

import common.logger.Logger;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command.GetUserBellNotificationsCommand;
import f1_Info.entry_points.user.commands.bell_notification_commands.mark_bell_notifications_as_opened_command.MarkBellNotificationsAsOpenedCommand;
import f1_Info.entry_points.user.commands.profile_picture_commands.get_user_profile_picture_command.GetUserProfilePictureCommand;
import f1_Info.entry_points.user.commands.profile_picture_commands.update_user_profile_picture_command.UpdateUserProfilePictureCommand;
import f1_Info.entry_points.user.commands.update_user_settings_command.Database;
import f1_Info.entry_points.user.commands.update_user_settings_command.NewUserSettingsRequestBody;
import f1_Info.entry_points.user.commands.update_user_settings_command.UpdateUserSettingsCommand;
import f1_Info.entry_points.user.commands.update_user_settings_command.UserSettingsValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/User")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class UserEndpoint {
    private static final long MAX_PROFILE_ICON_SIZE_IN_BYTES = 500000; // 500 MB

    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Logger mLogger;
    private final Database mUpdateUserSettingsDatabase;
    private final f1_Info.entry_points.user.commands.bell_notification_commands.Database mBellNotificationDatabase;
    private final f1_Info.entry_points.user.commands.profile_picture_commands.Database mProfilePictureDatabase;

    @PutMapping("/settings")
    public ResponseEntity<?> updateUserSettings(@RequestBody final NewUserSettingsRequestBody newUserSettings) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (newUserSettings == null || !UserSettingsValidator.newUserSettingsAreOfValidFormat(newUserSettings)) {
                throw new BadRequestException();
            }

            return new UpdateUserSettingsCommand(userId, newUserSettings, mUpdateUserSettingsDatabase);
        });
    }

    @PutMapping("/profile-icon")
    public ResponseEntity<?> updateProfileIcon(@RequestParam("file") MultipartFile file) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (!isImageValid(file)) {
                throw new BadRequestException();
            }
            return new UpdateUserProfilePictureCommand(userId, file, mProfilePictureDatabase);
        });
    }

    @ResponseBody
    @GetMapping(path = "/profile-icon", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getUserProfilePicture() {
        return new GetUserProfilePictureCommand(mEndpointHelper.getUserIdFromSession(mHttpServletRequest), mProfilePictureDatabase, mLogger).execute();
    }

    @GetMapping("/bell-notifications")
    public ResponseEntity<?> getBellNotifications() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new GetUserBellNotificationsCommand(userId, mBellNotificationDatabase));
    }

    @PutMapping("/bell-notifications-opened-state")
    public ResponseEntity<?> markBellNotificationsAsOpened() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new MarkBellNotificationsAsOpenedCommand(userId, mBellNotificationDatabase));
    }

    private boolean isImageValid(final MultipartFile file) {
        try {
            final BufferedImage image = ImageIO.read(file.getInputStream());
            return image != null || file.getSize() <= MAX_PROFILE_ICON_SIZE_IN_BYTES;
        } catch (final IOException e) {
            return false;
        }
    }

}
