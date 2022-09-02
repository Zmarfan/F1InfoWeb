package f1_Info.entry_points.development.manager_commands.mark_feedback_item_as_complete_command;


import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.services.bell_notification_send_out_service.BellNotificationIcon;
import f1_Info.services.bell_notification_send_out_service.BellNotificationSendOutService;
import f1_Info.services.bell_notification_send_out_service.CreateNotificationParameters;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@AllArgsConstructor(onConstructor=@__({@Autowired}))
@Component
public class MarkFeedbackItemAsCompleteLogic {
    private final BellNotificationSendOutService mBellNotificationSendOutService;
    private final Database mDatabase;

    public void markFeedbackItemAsComplete(final long itemId) throws SQLException {
        if (!mDatabase.canMarkFeedbackItemAsComplete(itemId)) {
            throw new BadRequestException();
        }

        mDatabase.markFeedbackItemAsComplete(itemId);

        sendBellNotification(itemId);
    }

    private void sendBellNotification(final long itemId) throws SQLException {
        final FeedbackAuthorInfoRecord authorRecord = mDatabase.getFeedbackAuthorInfo(itemId);

        mBellNotificationSendOutService.sendBellNotification(new CreateNotificationParameters(
            authorRecord.getUserId(),
            BellNotificationIcon.HAPPY_SMILEY,
            "bellMessages.completeFeedback",
            Map.of("feedback", authorRecord.getFeedback())
        ));
    }
}
