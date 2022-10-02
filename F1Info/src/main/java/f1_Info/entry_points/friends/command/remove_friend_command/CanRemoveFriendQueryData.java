package f1_Info.entry_points.friends.command.remove_friend_command;

import database.IQueryData;
import lombok.Value;

@Value
public class CanRemoveFriendQueryData implements IQueryData<Boolean> {
    long m0UserId;
    long m1FriendUserId;

    @Override
    public String getStoredProcedureName() {
        return "can_remove_friend";
    }
}
