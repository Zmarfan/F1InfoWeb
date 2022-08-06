package f1_Info.entry_points.user.commands.update_user_settings_command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static f1_Info.entry_points.user.commands.update_user_settings_command.UserSettingsValidator.MAX_DISPLAY_NAME_LENGTH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserSettingsValidatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "   " })
    void should_return_false_if_display_name_is_null_or_empty(final String displayName) {
        assertFalse(UserSettingsValidator.newUserSettingsAreOfValidFormat(new NewUserSettingsRequestBody(displayName)));
    }

    @Test
    void should_return_false_if_display_name_is_longer_than_max_length() {
        assertFalse(UserSettingsValidator.newUserSettingsAreOfValidFormat(new NewUserSettingsRequestBody("1".repeat(MAX_DISPLAY_NAME_LENGTH + 1))));
    }

    @Test
    void should_return_true_if_display_name_is_valid() {
        assertTrue(UserSettingsValidator.newUserSettingsAreOfValidFormat(new NewUserSettingsRequestBody("1".repeat(MAX_DISPLAY_NAME_LENGTH))));
    }

    @Test
    void should_return_false_if_new_display_name_is_same_as_current() {
        assertFalse(UserSettingsValidator.userSettingsAreValid(new CurrentUserSettingsRecord("yo"), new NewUserSettingsRequestBody("yo")));
    }

    @Test
    void should_return_true_if_new_display_name_is_different_from_current() {
        assertTrue(UserSettingsValidator.userSettingsAreValid(new CurrentUserSettingsRecord("yo"), new NewUserSettingsRequestBody("lo")));
    }
}
