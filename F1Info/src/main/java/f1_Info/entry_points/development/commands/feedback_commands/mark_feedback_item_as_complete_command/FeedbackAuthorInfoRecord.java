package f1_Info.entry_points.development.commands.feedback_commands.mark_feedback_item_as_complete_command;

import lombok.Value;

@Value
public class FeedbackAuthorInfoRecord {
    long mUserId;
    String mFeedback;
}