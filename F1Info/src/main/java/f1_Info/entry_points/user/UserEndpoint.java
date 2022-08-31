package f1_Info.entry_points.user;

import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command.GetUserBellNotificationsCommand;
import f1_Info.entry_points.user.commands.bell_notification_commands.mark_bell_notifications_as_opened_command.MarkBellNotificationsAsOpenedCommand;
import f1_Info.entry_points.user.commands.update_user_settings_command.Database;
import f1_Info.entry_points.user.commands.update_user_settings_command.NewUserSettingsRequestBody;
import f1_Info.entry_points.user.commands.update_user_settings_command.UpdateUserSettingsCommand;
import f1_Info.entry_points.user.commands.update_user_settings_command.UserSettingsValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/User")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class UserEndpoint {
    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final Database mUpdateUserSettingsDatabase;
    private final f1_Info.entry_points.user.commands.bell_notification_commands.Database mBellNotificationDatabase;

    @PostMapping("/update-settings")
    public ResponseEntity<?> updateUserSettings(@RequestBody final NewUserSettingsRequestBody newUserSettings) {
        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> {
            if (newUserSettings == null || !UserSettingsValidator.newUserSettingsAreOfValidFormat(newUserSettings)) {
                throw new BadRequestException();
            }

            return new UpdateUserSettingsCommand(userId, newUserSettings, mUpdateUserSettingsDatabase);
        });
    }

    @GetMapping("/bell-notifications")
    public ResponseEntity<?> getBellNotifications() {
        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> new GetUserBellNotificationsCommand(userId, mBellNotificationDatabase));
    }

    @PostMapping("/mark-bell-notifications-opened")
    public ResponseEntity<?> markBellNotificationsAsOpened() {
        return mEndpointHelper.authorizeAndRun(mHttpServletRequest, userId -> new MarkBellNotificationsAsOpenedCommand(userId, mBellNotificationDatabase));
    }
}
