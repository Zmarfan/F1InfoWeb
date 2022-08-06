package f1_Info.entry_points.user.commands;

public class UserSettingsValidator {
    public static boolean userSettingsAreValid(final NewUserSettingsRequestBody newUserSettings) {
        return newUserSettings.getDisplayName().trim().length() > 0;
    }
}
