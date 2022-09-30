package f1_Info.entry_points.friends;

import common.configuration.Configuration;
import common.configuration.ConfigurationRulesTestBuilder;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendCodeHandlerTest {
    private static final long USER_ID = 1;
    private static final String USER_ID_FRIEND_CODE = "qzxkXG8ZWa";
    private static final long VALID_ID_INVALID_USER = 3;
    private static final String VALID_ID_INVALID_USER_FRIEND_CODE = "7nVmw2kNpb";

    @Mock
    Configuration mConfiguration;

    @Mock
    UserManager mUserManager;

    @Mock
    Logger mLogger;

    @BeforeEach
    void init() throws MalformedEmailException {
        when(mConfiguration.getRules()).thenReturn(ConfigurationRulesTestBuilder.builder(true).build());
    }

    @Test
    void should_return_friend_code_from_user_id() {
        assertEquals(USER_ID_FRIEND_CODE, createHandler().friendCodeFromUserId(USER_ID));
    }

    @Test
    void should_return_empty_optional_if_provided_invalid_friend_code() {
        assertTrue(createHandler().userIdFromFriendCode("asdfasdf").isEmpty());
    }

    @Test
    void should_return_empty_optional_if_provided_valid_conversion_to_id_but_that_user_does_not_exist() throws SQLException {
        when(mUserManager.userExist(VALID_ID_INVALID_USER)).thenReturn(false);

        assertTrue(createHandler().userIdFromFriendCode(VALID_ID_INVALID_USER_FRIEND_CODE).isEmpty());
    }

    @Test
    void should_return_user_id_if_friend_code_can_be_parsed_to_existing_user() throws SQLException {
        when(mUserManager.userExist(USER_ID)).thenReturn(true);

        assertEquals(USER_ID, createHandler().userIdFromFriendCode(USER_ID_FRIEND_CODE).orElseThrow());
    }

    @Test
    void should_return_empty_optional_if_unable_to_check_if_user_exists() throws SQLException {
        when(mUserManager.userExist(VALID_ID_INVALID_USER)).thenThrow(new SQLException());

        assertTrue(createHandler().userIdFromFriendCode(VALID_ID_INVALID_USER_FRIEND_CODE).isEmpty());
    }

    @Test
    void should_log_severe_if_unable_to_check_if_user_exists() throws SQLException {
        when(mUserManager.userExist(VALID_ID_INVALID_USER)).thenThrow(new SQLException());

        createHandler().userIdFromFriendCode(VALID_ID_INVALID_USER_FRIEND_CODE);

        verify(mLogger).severe(anyString(), eq(FriendCodeHandler.class), anyString(), any(SQLException.class));
    }

    private FriendCodeHandler createHandler() {
        return new FriendCodeHandler(mConfiguration, mUserManager, mLogger);
    }
}
