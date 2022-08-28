package f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CreateFeedbackItemQueryData implements IQueryData<Void> {
    long m0UserId;
    String m1Text;

    @Override
    public String getStoredProcedureName() {
        return "create_feedback_item";
    }
}
