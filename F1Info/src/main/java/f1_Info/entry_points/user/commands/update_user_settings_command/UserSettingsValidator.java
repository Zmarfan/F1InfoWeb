package f1_Info.entry_points.user.commands.update_user_settings_command;

import java.util.Objects;

public class UserSettingsValidator {
    public static boolean newUserSettingsAreOfValidFormat(final NewUserSettingsRequestBody newSettings) {
        return newSettings.getDisplayName().trim().length() > 0;

    }

    public static boolean userSettingsAreValid(final CurrentUserSettingsRecord currentSettings, final NewUserSettingsRequestBody newSettings) {
        return !Objects.equals(currentSettings.getDisplayName(), newSettings.getDisplayName());
    }
}
