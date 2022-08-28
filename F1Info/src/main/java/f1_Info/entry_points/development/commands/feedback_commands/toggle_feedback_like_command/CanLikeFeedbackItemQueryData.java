package f1_Info.entry_points.development.commands.feedback_commands.toggle_feedback_like_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanLikeFeedbackItemQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1ItemId;

    @Override
    public String getStoredProcedureName() {
        return "can_like_feedback_item";
    }
}
