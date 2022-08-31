package f1_Info.services.bell_notification_send_out_service;

import common.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BellNotificationSendOutServiceTest {
    private static final long USER_ID = 123;
    private static final long NOTIFICATION_ID = 12;
    private static final Map<String, String> PARAMETERS = Map.of("key1", "value1", "key2", "value2");

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    BellNotificationSendOutService mService;

    @Test
    void should_log_severe_if_unable_to_create_notification() throws SQLException {
        final CreateNotificationParameters parameters = new CreateNotificationParameters(USER_ID, BellNotificationIcon.HAPPY_SMILEY, "key", emptyMap());
        when(mDatabase.createBellNotification(parameters)).thenThrow(new SQLException());

        mService.sendBellNotification(parameters);

        verify(mLogger).severe(anyString(), eq(BellNotificationSendOutService.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_log_severe_if_unable_to_create_notification_parameters() throws SQLException {
        final CreateNotificationParameters parameters = new CreateNotificationParameters(USER_ID, BellNotificationIcon.HAPPY_SMILEY, "key", PARAMETERS);
        when(mDatabase.createBellNotification(parameters)).thenReturn(NOTIFICATION_ID);
        doThrow(new SQLException()).when(mDatabase).createBellNotificationParameters(anyList());

        mService.sendBellNotification(parameters);

        verify(mLogger).severe(anyString(), eq(BellNotificationSendOutService.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_create_notification_with_as_many_parameters_as_provided() throws SQLException {
        final CreateNotificationParameters parameters = new CreateNotificationParameters(USER_ID, BellNotificationIcon.HAPPY_SMILEY, "key", PARAMETERS);
        when(mDatabase.createBellNotification(parameters)).thenReturn(NOTIFICATION_ID);

        mService.sendBellNotification(parameters);

        verify(mDatabase).createBellNotificationParameters(List.of(
            new CreateBellNotificationParameterQueryData(NOTIFICATION_ID, "key2", "value2"),
            new CreateBellNotificationParameterQueryData(NOTIFICATION_ID, "key1", "value1")
        ));
    }

    @Test
    void should_not_create_any_notification_parameters_if_none_are_provided() throws SQLException {
        final CreateNotificationParameters parameters = new CreateNotificationParameters(USER_ID, BellNotificationIcon.HAPPY_SMILEY, "key", emptyMap());

        mService.sendBellNotification(parameters);

        verify(mDatabase, never()).createBellNotificationParameters(anyList());
    }
}
