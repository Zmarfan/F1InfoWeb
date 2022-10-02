package f1_Info.entry_points.friends.command.remove_friend_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RemoveFriendRequestBody {
    long mUserId;

    @JsonCreator
    public RemoveFriendRequestBody(
        @JsonProperty("userId") final long userID
    ) {
        mUserId = userID;
    }
}
