package f1_Info.entry_points.development.commands.feedback_commands.like_feedback_item_command;

import database.IQueryData;
import lombok.Value;

@Value
public class LikeFeedbackItemQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1ItemId;

    @Override
    public String getStoredProcedureName() {
        return "like_feedback_item";
    }
}
