package f1_Info.entry_points.friends.command.get_friends_info_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetFriendRequestsQueryData implements IQueryData<FriendRequestRecord> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "get_friend_requests";
    }
}
