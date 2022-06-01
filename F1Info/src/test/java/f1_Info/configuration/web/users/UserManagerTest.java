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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {
    private static final String EMAIL = "test@email.com";
    private static final String INVALID_EMAIL = "test@emailcom";

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    UserManager mUserManager;

    @Test
    void should_return_user_details_for_matching_user_in_database() throws MalformedEmailException, SQLException {
        final F1UserDetails expectedUserDetails = new F1UserDetails(1L, new Email(EMAIL), "hashed", Authority.USER, true);

        when(mDatabase.getUserDetailsFromEmail(any(Email.class))).thenReturn(Optional.of(createUserRecord()));

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
    void should_return_true_if_user_exist() throws SQLException, MalformedEmailException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.of(createUserRecord()));
        assertTrue(mUserManager.userExists(new Email(EMAIL)));
    }

    @Test
    void should_return_false_if_user_does_not_exist() throws SQLException, MalformedEmailException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());
        assertFalse(mUserManager.userExists(new Email(EMAIL)));
    }

    @Test
    void should_return_false_if_sql_exception_occurs_when_checking_if_user_exist() throws SQLException, MalformedEmailException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenThrow(new SQLException());
        assertFalse(mUserManager.userExists(new Email(EMAIL)));
    }

    @Test
    void should_log_severe_if_sql_exception_occurs_when_checking_if_user_exists() throws SQLException, MalformedEmailException {
        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenThrow(new SQLException());

        mUserManager.userExists(new Email(EMAIL));

        verify(mLogger).severe(anyString(), eq(UserManager.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_throw_unable_to_register_if_user_already_exists_when_trying_to_create_new() throws MalformedEmailException, SQLException {
        final F1UserDetails registerUserDetails = createRegisterUserDetails();

        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.of(createUserRecord()));

        assertThrows(UnableToRegisterUserException.class, () -> mUserManager.registerUser(registerUserDetails));
    }

    @Test
    void should_return_id_of_created_user_when_registering_new_user() throws MalformedEmailException, SQLException {
        final F1UserDetails userDetails = createRegisterUserDetails();

        when(mDatabase.getUserDetailsFromEmail(new Email(EMAIL))).thenReturn(Optional.empty());
        when(mDatabase.createUser(userDetails)).thenReturn(1L);

        assertEquals(1, mUserManager.registerUser(userDetails));
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

    private UserDetailsRecord createUserRecord() throws MalformedEmailException {
        return new UserDetailsRecord(1, new Email(EMAIL), "hashed", Authority.USER.getAuthority(), true);
    }

    private F1UserDetails createRegisterUserDetails() throws MalformedEmailException {
        return F1UserDetails.createNewUser(new Email(EMAIL), "password");
    }
}
