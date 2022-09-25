package f1_Info.entry_points.friends.command.search_friend_command;

import f1_Info.entry_points.friends.FriendStatus;
import lombok.Value;

@Value
public class SearchFriendResponse {
    String mFriendCode;
    String mDisplayName;
    int mFriendsInCommon;
    FriendStatus mFriendStatus;
}
