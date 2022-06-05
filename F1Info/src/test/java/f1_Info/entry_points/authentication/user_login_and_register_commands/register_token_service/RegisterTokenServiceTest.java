package f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.helpers.DateFactory;
import common.logger.Logger;
import common.utils.DateUtils;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterTokenServiceTest {
    private static final long USER_ID = 23L;
    private static final UUID TOKEN = UUID.randomUUID();
    private static final LocalDateTime TOKEN_CREATION_TIME = DateUtils.parseDateTime("2021-03-08 12:23");
    private static final LocalDateTime VALID_TOKEN_TIME = DateUtils.parseDateTime("2021-03-08 12:30");
    private static final LocalDateTime INVALID_TOKEN_TIME = DateUtils.parseDateTime("2021-03-08 14:30");

    @Mock
    Database mDatabase;

    @Mock
    DateFactory mDateFactory;

    @Mock
    Logger mLogger;

    @InjectMocks
    RegisterTokenService mRegisterTokenService;

    @Test
    void should_insert_registration_token_for_user() throws SQLException {
        mRegisterTokenService.insertRegistrationTokenForUser(USER_ID, TOKEN);

        verify(mDatabase).insertRegistrationTokenForUser(USER_ID, TOKEN);
    }

    @Test
    void should_throw_unable_to_register_exception_if_unable_to_insert_registration_token() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).insertRegistrationTokenForUser(USER_ID, TOKEN);

        assertThrows(UnableToRegisterUserException.class, () -> mRegisterTokenService.insertRegistrationTokenForUser(USER_ID, TOKEN));
    }

    @Test
    void should_log_severe_if_unable_to_insert_registration_token() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).insertRegistrationTokenForUser(USER_ID, TOKEN);

        assertThrows(UnableToRegisterUserException.class, () -> mRegisterTokenService.insertRegistrationTokenForUser(USER_ID, TOKEN));
        verify(mLogger).severe(anyString(), eq(RegisterTokenService.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_return_token_record_from_token_when_token_is_not_expired() throws MalformedEmailException, SQLException {
        final RegistrationTokenRecord tokenRecord = new RegistrationTokenRecord(USER_ID, new Email("email@test.com"), TOKEN_CREATION_TIME);

        when(mDatabase.findDisabledUserFromToken(TOKEN)).thenReturn(Optional.of(tokenRecord));
        when(mDateFactory.nowTime()).thenReturn(VALID_TOKEN_TIME);

        assertEquals(tokenRecord, mRegisterTokenService.findDisabledUserFromToken(TOKEN).orElseThrow());
    }

    @Test
    void should_return_empty_optional_if_no_token_exists_for_user() throws SQLException {
        when(mDatabase.findDisabledUserFromToken(TOKEN)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), mRegisterTokenService.findDisabledUserFromToken(TOKEN));
    }

    @Test
    void should_return_empty_optional_if_retrieved_token_is_expired() throws MalformedEmailException, SQLException {
        final RegistrationTokenRecord tokenRecord = new RegistrationTokenRecord(USER_ID, new Email("email@test.com"), TOKEN_CREATION_TIME);

        when(mDatabase.findDisabledUserFromToken(TOKEN)).thenReturn(Optional.of(tokenRecord));
        when(mDateFactory.nowTime()).thenReturn(INVALID_TOKEN_TIME);

        assertEquals(Optional.empty(), mRegisterTokenService.findDisabledUserFromToken(TOKEN));
    }

    @Test
    void should_return_empty_optional_if_unable_to_query_for_token_record() throws SQLException {
        when(mDatabase.findDisabledUserFromToken(TOKEN)).thenThrow(new SQLException());

        assertEquals(Optional.empty(), mRegisterTokenService.findDisabledUserFromToken(TOKEN));
    }

    @Test
    void should_log_severe_if_unable_to_query_for_token_record() throws SQLException {
        when(mDatabase.findDisabledUserFromToken(TOKEN)).thenThrow(new SQLException());

        mRegisterTokenService.findDisabledUserFromToken(TOKEN);
        
        verify(mLogger).severe(anyString(), eq(RegisterTokenService.class), anyString(), any(SQLException.class));
    }
}
