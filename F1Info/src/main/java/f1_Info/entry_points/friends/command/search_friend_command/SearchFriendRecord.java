package f1_Info.entry_points.friends.command.search_friend_command;

import f1_Info.entry_points.friends.FriendStatus;
import lombok.Value;

@Value
public class SearchFriendRecord {
    String mDisplayName;
    int mFriendsInCommon;
    FriendStatus mFriendStatus;

    public SearchFriendRecord(final String displayName, final int friendsInCommon, final String friendStatus) {
        mDisplayName = displayName;
        mFriendsInCommon = friendsInCommon;
        mFriendStatus = FriendStatus.fromString(friendStatus);
    }
}
