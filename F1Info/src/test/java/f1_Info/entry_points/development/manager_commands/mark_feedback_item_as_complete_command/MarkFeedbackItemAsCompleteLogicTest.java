package f1_Info.entry_points.development.manager_commands.mark_feedback_item_as_complete_command;

import f1_Info.entry_points.development.manager_commands.feedback_commands.Database;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.FeedbackAuthorInfoRecord;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.MarkFeedbackItemAsCompleteLogic;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.services.bell_notification_send_out_service.BellNotificationSendOutService;
import f1_Info.services.bell_notification_send_out_service.CreateNotificationParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkFeedbackItemAsCompleteLogicTest {
    private static final long ITEM_ID = 5412;

    @Mock
    BellNotificationSendOutService mBellNotificationSendOutService;

    @Mock
    Database mDatabase;

    @InjectMocks
    MarkFeedbackItemAsCompleteLogic mLogic;

    @Test
    void should_throw_bad_request_if_unable_to_mark_feedback_as_complete() throws SQLException {
        when(mDatabase.canMarkFeedbackItemAsComplete(ITEM_ID)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> mLogic.markFeedbackItemAsComplete(ITEM_ID));
    }

    @Test
    void should_throw_sql_exception_if_unable_to_mark_feedback_item_as_complete() throws SQLException {
        when(mDatabase.canMarkFeedbackItemAsComplete(ITEM_ID)).thenReturn(true);
        doThrow(new SQLException()).when(mDatabase).markFeedbackItemAsComplete(ITEM_ID);

        assertThrows(SQLException.class, () -> mLogic.markFeedbackItemAsComplete(ITEM_ID));
    }

    @Test
    void should_throw_sql_exception_if_unable_to_fetch_author_information() throws SQLException {
        when(mDatabase.canMarkFeedbackItemAsComplete(ITEM_ID)).thenReturn(true);
        doThrow(new SQLException()).when(mDatabase).getFeedbackAuthorInfo(ITEM_ID);

        assertThrows(SQLException.class, () -> mLogic.markFeedbackItemAsComplete(ITEM_ID));
    }

    @Test
    void should_mark_feedback_item_as_complete_then_send_notification() throws SQLException {
        when(mDatabase.canMarkFeedbackItemAsComplete(ITEM_ID)).thenReturn(true);
        when(mDatabase.getFeedbackAuthorInfo(ITEM_ID)).thenReturn(new FeedbackAuthorInfoRecord(12, "feedback"));

        mLogic.markFeedbackItemAsComplete(ITEM_ID);

        final InOrder inOrder = inOrder(mDatabase, mBellNotificationSendOutService);
        inOrder.verify(mDatabase).markFeedbackItemAsComplete(ITEM_ID);
        inOrder.verify(mBellNotificationSendOutService).sendBellNotification(any(CreateNotificationParameters.class));
    }
}
