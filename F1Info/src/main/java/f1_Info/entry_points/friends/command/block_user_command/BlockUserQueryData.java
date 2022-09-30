package f1_Info.entry_points.friends.command.block_user_command;

import database.IQueryData;
import lombok.Value;

@Value
public class BlockUserQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1BlockUserId;

    @Override
    public String getStoredProcedureName() {
        return "block_user";
    }
}
