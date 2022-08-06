package f1_Info.entry_points.user.commands.update_user_settings_command;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class UserSettingsValidator {
    private static final int MAX_DISPLAY_NAME_LENGTH = 20;

    public static boolean newUserSettingsAreOfValidFormat(final NewUserSettingsRequestBody newSettings) {
        return newSettings.getDisplayName().trim().length() > 0 && newSettings.getDisplayName().length() <= MAX_DISPLAY_NAME_LENGTH;
    }

    public static boolean userSettingsAreValid(final CurrentUserSettingsRecord currentSettings, final NewUserSettingsRequestBody newSettings) {
        return !Objects.equals(currentSettings.getDisplayName(), newSettings.getDisplayName());
    }
}
