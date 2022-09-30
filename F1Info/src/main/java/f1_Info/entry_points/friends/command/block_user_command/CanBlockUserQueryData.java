package f1_Info.entry_points.friends.command.block_user_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanBlockUserQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1BlockUserId;

    @Override
    public String getStoredProcedureName() {
        return "can_block_user";
    }
}
