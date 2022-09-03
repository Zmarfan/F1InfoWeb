package f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FeedbackItemRecord {
    int mId;
    String mText;
    LocalDateTime mDate;
    boolean mCompleted;
    boolean mNotDoing;
    String mAuthorDisplayName;
    int mLikesNotFromUser;
    boolean mIsOwn;
    boolean mLikedByUser;
}
