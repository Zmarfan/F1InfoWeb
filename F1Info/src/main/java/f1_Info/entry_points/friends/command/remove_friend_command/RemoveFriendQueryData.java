package f1_Info.entry_points.friends.command.remove_friend_command;

import database.IQueryData;
import lombok.Value;

@Value
public class RemoveFriendQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1FriendUserId;

    @Override
    public String getStoredProcedureName() {
        return "remove_friend";
    }
}
