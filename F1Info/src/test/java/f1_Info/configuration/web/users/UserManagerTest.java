package f1_Info.configuration.web.users;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        final F1UserDetails expectedUserDetails = new F1UserDetails(1, new Email(EMAIL), "hashed", Authority.USER, true);

        when(mDatabase.getUserDetailsFromEmail(any(Email.class))).thenReturn(
            Optional.of(new UserDetailsRecord(1, new Email(EMAIL), "hashed", Authority.USER.getAuthority(), true))
        );


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
}
