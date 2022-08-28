package f1_Info.entry_points.development.commands.get_feedback_items_command;

import lombok.Value;

@Value
public class FeedbackItemResponse {
    long mFeedbackId;
    String mText;
    String mAuthorDisplayName;
    String mDate;
    long mAmountOfUpVotesNotYou;
    boolean mIsOwn;
    boolean mHasGivenUpVote;
}
