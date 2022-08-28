package f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command;

import database.IQueryData;
import lombok.Value;

@Value
public class IsUserOwnerOfFeedbackItemQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1ItemId;

    @Override
    public String getStoredProcedureName() {
        return "is_user_owner_of_feedback_item";
    }
}
