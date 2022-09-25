package f1_Info.entry_points.friends.command.send_friend_request_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanSendFriendRequestQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1SendRequestUserId;

    @Override
    public String getStoredProcedureName() {
        return "can_send_friend_request";
    }
}
