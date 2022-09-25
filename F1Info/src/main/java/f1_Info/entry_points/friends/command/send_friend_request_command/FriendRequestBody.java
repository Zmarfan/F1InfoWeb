package f1_Info.entry_points.friends.command.send_friend_request_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FriendRequestBody {
    String mFriendCode;

    @JsonCreator
    public FriendRequestBody(
        @JsonProperty("friendCode") final String friendCode
    ) {
        mFriendCode = friendCode;
    }
}
