package f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command;

import lombok.Value;

@Value
public class FeedbackItemResponse {
    long mFeedbackId;
    String mText;
    String mAuthorDisplayName;
    String mDate;
    boolean mCompleted;
    boolean mNotDoing;
    long mAmountOfUpVotesNotYou;
    boolean mIsOwn;
    boolean mHasGivenUpVote;

    public FeedbackItemResponse(final FeedbackItemRecord feedbackRecord) {
        mFeedbackId = feedbackRecord.getId();
        mText = feedbackRecord.getText();
        mAuthorDisplayName = feedbackRecord.getAuthorDisplayName();
        mDate = feedbackRecord.getDate().toLocalDate().toString();
        mCompleted = feedbackRecord.getCompleted();
        mNotDoing = feedbackRecord.getNotDoing();
        mAmountOfUpVotesNotYou = feedbackRecord.getLikesNotFromUser();
        mIsOwn = feedbackRecord.getIsOwn();
        mHasGivenUpVote = feedbackRecord.getLikedByUser();
    }
}
