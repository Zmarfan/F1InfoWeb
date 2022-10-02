package f1_Info.entry_points.friends.command.get_friends_info_command;

import lombok.Value;

import java.util.List;

@Value
public class FriendsInfoResponse {
    String mMyFriendCode;
    List<FriendRequestRecord> mFriendRequests;
    List<FriendRecord> mFriends;
}
