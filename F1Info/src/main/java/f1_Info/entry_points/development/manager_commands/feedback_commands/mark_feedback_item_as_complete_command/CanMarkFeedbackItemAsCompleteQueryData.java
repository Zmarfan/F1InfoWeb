package f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanMarkFeedbackItemAsCompleteQueryData implements IQueryData<Boolean> {
    long mItemId;

    @Override
    public String getStoredProcedureName() {
        return "can_mark_feedback_item_as_complete";
    }
}
