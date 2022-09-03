package f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_will_not_do_command;

import database.IQueryData;
import lombok.Value;

@Value
public class MarkItemAsWillNotDo implements IQueryData<Void> {
    long mItemId;

    @Override
    public String getStoredProcedureName() {
        return "mark_feedback_item_as_will_not_do";
    }
}
