package f1_Info.entry_points.friends.command.send_friend_request_command;

import lombok.Value;

@Value
public class SendFriendRequestRecord {
    boolean mCanSendFriendRequest;
    boolean mReceiverHasBlockedUser;
    String mDisplayName;
}
