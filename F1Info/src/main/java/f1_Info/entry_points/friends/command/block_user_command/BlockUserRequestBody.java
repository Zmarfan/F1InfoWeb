package f1_Info.entry_points.friends.command.block_user_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class BlockUserRequestBody {
    long mUserId;

    @JsonCreator
    public BlockUserRequestBody(
        @JsonProperty("userId") final long userID
    ) {
        mUserId = userID;
    }
}
