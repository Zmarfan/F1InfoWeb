package f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetFeedbackAuthorInfoQueryData implements IQueryData<FeedbackAuthorInfoRecord> {
    long mItemId;

    @Override
    public String getStoredProcedureName() {
        return "get_feedback_author_info";
    }
}
