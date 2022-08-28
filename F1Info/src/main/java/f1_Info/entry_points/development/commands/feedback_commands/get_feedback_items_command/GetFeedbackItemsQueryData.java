package f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetFeedbackItemsQueryData implements IQueryData<FeedbackItemRecord> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "get_all_feedback_items";
    }
}
