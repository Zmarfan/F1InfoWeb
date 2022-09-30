package f1_Info.entry_points.friends.command.answer_friend_request_commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AnswerFriendRequestRequestBody {
    long mUserId;

    @JsonCreator
    public AnswerFriendRequestRequestBody(
        @JsonProperty("userId") final long userID
    ) {
        mUserId = userID;
    }
}
