package f1_Info.entry_points.user.commands.update_user_settings_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UpdateUserSettingsCommand implements Command {
    private final long mUserId;
    private final NewUserSettingsRequestBody mNewUserSettings;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final CurrentUserSettingsRecord currentSettings = mDatabase.getCurrentUserSettings(mUserId);
        if (!UserSettingsValidator.userSettingsAreValid(currentSettings, mNewUserSettings)) {
            return badRequest();
        }

        mDatabase.updateSettings(mUserId, mNewUserSettings);
        return ok();
    }
}
