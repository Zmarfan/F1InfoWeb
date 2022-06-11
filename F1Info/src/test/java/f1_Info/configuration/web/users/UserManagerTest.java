package f1_Info.configuration.web.users;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.database.Database;
import f1_Info.configuration.web.users.database.UserDetailsRecord;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {
    private static final long USER_ID = 23L;
    private static final String EMAIL = "test@email.com";
    private static final String INVALID_EMAIL = "test@emailcom";
    private static final String PASSWORD = "SA761!asd87812h!";

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    UserManager mUserManager;

    @Test
    void should_return_user_details_for_matching_user_in_database() throws MalformedEmailException, SQLException {
        final F1UserDetails expectedUserDetails = new F1UserDetails(USER_ID, new Email(EMAIL), "hashed", Authority.USER, true);

        when(mDatabase.getUserDetailsFromEmail(any(Email.class))).thenReturn(Optional.of(createUserRecord(true)));

        assertEquals(expectedUserDetails, mUserManager.loadUserByUsername(EMAIL));
    }

    @Test
    void should_throw_username_not_found_exception_if_no_matching_user_was_found_in_database() {
        assertThrows(UsernameNotFoundException.class, () -> mUserManager.loadUserByUsername(EMAIL));
    }

    @Test
    void should_throw_username_not_found_exception_if_error_happened_while_fetching_user_details_from_database() throws SQLException {
        when(mDatabase.getUserDetailsFromEmail(any(Email.class))).thenThrow(new SQLException());
        assertThrows(UsernameNotFoundException.class, () -> mUserManager.loadUserByUsername(EMAIL));
    }

    @Test
    void should_log_severe_if_error_happened_while_fetching_user_details_from_database() throws SQLException {
        when(mDatabase.getUserDetailsFromEmail(any(Email.class))).thenThrow(new SQLException());

        assertThrows(UsernameNotFoundException.class, () -> mUserManager.loadUserByUsername(EMAIL));
        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_throw_username_not_found_exception_if_unable_to_convert_username_to_email() {
        assertThrows(UsernameNotFoundException.class, () -> mUserManager.loadUserByUsername(INVALID_EMAIL));
    }

    @Test
    void should_log_severe_if_unable_to_convert_username_to_email() {
        assertThrows(UsernameNotFoundException.class, () -> mUserManager.loadUserByUsername(INVALID_EMAIL));
        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(MalformedEmailException.class));
    }

    @Test
    void should_throw_unable_to_register_if_user_already_exists_and_is_enabled_when_trying_to_create_new() throws MalformedEmailException, SQLException {
        final F1UserDetails registerUserDetails = createRegisterUserDetails();

        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.of(createUserRecord(true)));

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.registerUser(registerUserDetails));
    }

    @Test
    void should_return_existent_user_id_if_user_exists_but_is_not_enabled_when_registering_user() throws MalformedEmailException, SQLException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.of(createUserRecord(false)));

        assertEquals(USER_ID, mUserManager.registerUser(createRegisterUserDetails()));
    }

    @Test
    void should_not_create_new_user_if_existent_user_is_disabled() throws MalformedEmailException, SQLException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.of(createUserRecord(false)));

        mUserManager.registerUser(createRegisterUserDetails());

        verify(mDatabase, never()).createUser(any(F1UserDetails.class));
    }

    @Test
    void should_return_id_of_created_user_when_registering_new_user() throws MalformedEmailException, SQLException {
        final F1UserDetails userDetails = createRegisterUserDetails();

        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());
        when(mDatabase.createUser(userDetails)).thenReturn(USER_ID);

        assertEquals(USER_ID, mUserManager.registerUser(userDetails));
    }

    @Test
    void should_log_info_when_registering_new_user() throws MalformedEmailException, SQLException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());

        final F1UserDetails userDetails = createRegisterUserDetails();
        mUserManager.registerUser(userDetails);

        verify(mLogger).info(anyString(), eq(UserManager.class), anyString());
    }

    @Test
    void should_throw_unable_to_register_user_exception_if_unable_to_create_user() throws MalformedEmailException, SQLException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());
        when(mDatabase.createUser(any(UserDetails.class))).thenThrow(new SQLException());

        final F1UserDetails userDetails = createRegisterUserDetails();
        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.registerUser(userDetails));
    }

    @Test
    void should_log_severe_if_unable_to_create_user() throws MalformedEmailException, SQLException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());
        when(mDatabase.createUser(any(UserDetails.class))).thenThrow(new SQLException());

        final F1UserDetails userDetails = createRegisterUserDetails();

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.registerUser(userDetails));
        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_enable_user() throws SQLException {
        mUserManager.enableUser(USER_ID);
        verify(mDatabase).enableUser(USER_ID);
    }

    @Test
    void should_throw_unable_to_register_user_exception_if_throwing_sql_exception_when_enabling_user() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).enableUser(USER_ID);

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.enableUser(USER_ID));
    }

    @Test
    void should_log_severe_when_enabling_user_if_unable_to() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).enableUser(USER_ID);

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.enableUser(USER_ID));
        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_new_password_for_user() throws SQLException {
        mUserManager.setNewPasswordForUser(USER_ID, PASSWORD);
        verify(mDatabase).setNewPasswordForUser(USER_ID, PASSWORD);
    }

    @Test
    void should_throw_unable_to_register_user_exception_if_throwing_sql_exception_when_setting_new_password_for_user() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).setNewPasswordForUser(USER_ID, PASSWORD);

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.setNewPasswordForUser(USER_ID, PASSWORD));
    }

    @Test
    void should_log_severe_when_setting_password_for_user_if_unable_to() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).setNewPasswordForUser(USER_ID, PASSWORD);

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.setNewPasswordForUser(USER_ID, PASSWORD));
        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(SQLException.class));
    }

    private UserDetailsRecord createUserRecord(final boolean enabled) throws MalformedEmailException {
        return new UserDetailsRecord(USER_ID, new Email(EMAIL), "hashed", Authority.USER.getAuthority(), enabled);
    }

    private F1UserDetails createRegisterUserDetails() throws MalformedEmailException {
        return F1UserDetails.createNewUser(new Email(EMAIL), "password");
    }
}
