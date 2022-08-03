package f1_Info.entry_points.authentication.commands.get_user_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetUserQueryData implements IQueryData<GetUserRecord> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "get_user_command_get_user";
    }
}
