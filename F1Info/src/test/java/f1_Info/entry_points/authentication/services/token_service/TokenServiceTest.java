package f1_Info.entry_points.authentication.services.token_service;

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
class TokenServiceTest {
    private static final long USER_ID = 23L;
    private static final String EMAIL = "email@test.com";
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
    TokenService mTokenService;

    @Test
    void should_insert_registration_token_for_user() throws SQLException {
        mTokenService.insertTokenForUser(USER_ID, TOKEN);

        verify(mDatabase).insertTokenForUser(USER_ID, TOKEN);
    }

    @Test
    void should_throw_unable_to_register_exception_if_unable_to_insert_registration_token() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).insertTokenForUser(USER_ID, TOKEN);

        assertThrows(UnableToRegisterUserException.class, () -> mTokenService.insertTokenForUser(USER_ID, TOKEN));
    }

    @Test
    void should_log_severe_if_unable_to_insert_registration_token() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).insertTokenForUser(USER_ID, TOKEN);

        assertThrows(UnableToRegisterUserException.class, () -> mTokenService.insertTokenForUser(USER_ID, TOKEN));
        verify(mLogger).severe(anyString(), eq(TokenService.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_return_token_record_from_token_when_token_is_not_expired() throws MalformedEmailException, SQLException {
        final TokenRecord tokenRecord = new TokenRecord(USER_ID, false, new Email(EMAIL), TOKEN_CREATION_TIME);

        when(mDatabase.findUserFromToken(TOKEN)).thenReturn(Optional.of(tokenRecord));
        when(mDateFactory.nowTime()).thenReturn(VALID_TOKEN_TIME);

        final UserInformation userInformation = new UserInformation(USER_ID, new Email(EMAIL), TokenStatusType.VALID);

        assertEquals(userInformation, mTokenService.findUserFromToken(TOKEN).orElseThrow());
    }

    @Test
    void should_return_empty_optional_if_no_token_exists_for_user() throws SQLException {
        when(mDatabase.findUserFromToken(TOKEN)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), mTokenService.findUserFromToken(TOKEN));
    }

    @Test
    void should_return_user_information_marked_as_expired_if_retrieved_token_is_expired() throws MalformedEmailException, SQLException {
        final TokenRecord tokenRecord = new TokenRecord(USER_ID, false, new Email(EMAIL), TOKEN_CREATION_TIME);

        when(mDatabase.findUserFromToken(TOKEN)).thenReturn(Optional.of(tokenRecord));
        when(mDateFactory.nowTime()).thenReturn(INVALID_TOKEN_TIME);

        final UserInformation userInformation = new UserInformation(USER_ID, new Email(EMAIL), TokenStatusType.TIMED_OUT);

        assertEquals(userInformation, mTokenService.findUserFromToken(TOKEN).orElseThrow());
    }

    @Test
    void should_return_user_information_marked_as_already_verified_if_retrieved_user_is_enabled() throws MalformedEmailException, SQLException {
        final TokenRecord tokenRecord = new TokenRecord(USER_ID, true, new Email(EMAIL), TOKEN_CREATION_TIME);

        when(mDatabase.findUserFromToken(TOKEN)).thenReturn(Optional.of(tokenRecord));

        final UserInformation userInformation = new UserInformation(USER_ID, new Email(EMAIL), TokenStatusType.ALREADY_VERIFIED);

        assertEquals(userInformation, mTokenService.findUserFromToken(TOKEN).orElseThrow());
    }

    @Test
    void should_return_empty_optional_if_unable_to_query_for_token_record() throws SQLException {
        when(mDatabase.findUserFromToken(TOKEN)).thenThrow(new SQLException());

        assertEquals(Optional.empty(), mTokenService.findUserFromToken(TOKEN));
    }

    @Test
    void should_log_severe_if_unable_to_query_for_token_record() throws SQLException {
        when(mDatabase.findUserFromToken(TOKEN)).thenThrow(new SQLException());

        mTokenService.findUserFromToken(TOKEN);

        verify(mLogger).severe(anyString(), eq(TokenService.class), anyString(), any(SQLException.class));
    }
}
