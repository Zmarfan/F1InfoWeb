package f1_Info.entry_points.development.commands.feedback_commands.remove_feedback_item_like_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanRemoveLikeFromFeedbackItemQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1ItemId;

    @Override
    public String getStoredProcedureName() {
        return "can_remove_like_from_feedback_item";
    }
}
