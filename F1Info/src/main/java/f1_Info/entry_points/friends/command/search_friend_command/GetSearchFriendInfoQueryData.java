package f1_Info.entry_points.friends.command.search_friend_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetSearchFriendInfoQueryData implements IQueryData<SearchFriendRecord> {
    long m0UserId;
    long m1SearchUserId;

    @Override
    public String getStoredProcedureName() {
        return "get_search_friend_info";
    }
}
