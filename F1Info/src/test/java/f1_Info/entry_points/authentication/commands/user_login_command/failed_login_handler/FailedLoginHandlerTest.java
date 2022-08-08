package f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler;

import common.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler.FailedLoginHandler.MAX_FAILED_ATTEMPTS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FailedLoginHandlerTest {
    private static final String IP = "192.168.12.4";

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks FailedLoginHandler mFailedLoginHandler;

    @Test
    void should_run_reset_amount_if_needed_before_fetching_failed_attempts_when_checking_if_eligible_to_login() throws SQLException {
        mFailedLoginHandler.canMakeAnotherLoginAttempt(IP);

        InOrder inOrder = inOrder(mDatabase);
        inOrder.verify(mDatabase).resetAmountOfFailedAttemptsIfWaitedLongEnough(IP);
        inOrder.verify(mDatabase).getAmountOfFailedAttempts(IP);
    }

    @Test
    void should_return_false_and_log_severe_if_unable_to_reset_failed_attempts_if_needed_when_checking_if_eligible_to_login() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).resetAmountOfFailedAttemptsIfWaitedLongEnough(IP);

        assertFalse(mFailedLoginHandler.canMakeAnotherLoginAttempt(IP));
        verify(mLogger).severe(anyString(), eq(FailedLoginHandler.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_return_false_and_log_severe_if_unable_to_fetch_failed_attempts_when_checking_if_eligible_to_login() throws SQLException {
        when(mDatabase.getAmountOfFailedAttempts(IP)).thenThrow(new SQLException());

        assertFalse(mFailedLoginHandler.canMakeAnotherLoginAttempt(IP));
        verify(mLogger).severe(anyString(), eq(FailedLoginHandler.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_return_false_if_failed_attempts_is_higher_than_max_failed_attempts_when_checking_if_eligible_to_login() throws SQLException {
        when(mDatabase.getAmountOfFailedAttempts(IP)).thenReturn(MAX_FAILED_ATTEMPTS + 1);

        assertFalse(mFailedLoginHandler.canMakeAnotherLoginAttempt(IP));
    }

    @ParameterizedTest
    @ValueSource(ints = { MAX_FAILED_ATTEMPTS, MAX_FAILED_ATTEMPTS - 1 })
    void should_return_true_if_failed_attempts_is_lower_or_equal_than_max_failed_attempts_when_checking_if_eligible_to_login(
        final int failedAttempts
    ) throws SQLException {
        when(mDatabase.getAmountOfFailedAttempts(IP)).thenReturn(failedAttempts);

        assertTrue(mFailedLoginHandler.canMakeAnotherLoginAttempt(IP));
    }

    @Test
    void should_log_severe_if_unable_to_add_failed_login_attempt() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).addFailedLoginAttempt(IP);

        mFailedLoginHandler.addFailedLoginAttempt(IP);

        verify(mLogger).severe(anyString(), eq(FailedLoginHandler.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_log_severe_if_unable_to_reset_failed_attempts() throws SQLException {
        doThrow(new SQLException()).when(mDatabase).resetFailedAttempts(IP);

        mFailedLoginHandler.resetFailedAttempts(IP);

        verify(mLogger).severe(anyString(), eq(FailedLoginHandler.class), anyString(), any(SQLException.class));
    }
}
