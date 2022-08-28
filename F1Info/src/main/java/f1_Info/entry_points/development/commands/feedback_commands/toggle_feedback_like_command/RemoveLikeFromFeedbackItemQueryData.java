package f1_Info.entry_points.development.commands.feedback_commands.toggle_feedback_like_command;

import database.IQueryData;
import lombok.Value;

@Value
public class RemoveLikeFromFeedbackItemQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1ItemId;

    @Override
    public String getStoredProcedureName() {
        return "remove_like_from_feedback_item";
    }
}
