package f1_Info.entry_points.friends.command.get_friends_info_command;

import lombok.Value;

@Value
public class FriendRequestRecord {
    long mUserId;
    String mDisplayName;
}
