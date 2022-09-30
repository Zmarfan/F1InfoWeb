package f1_Info.entry_points.friends.command.answer_friend_request_commands.decline;

import database.IQueryData;
import lombok.Value;

@Value
public class DeclineFriendRequestQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1AnswerUserId;

    @Override
    public String getStoredProcedureName() {
        return "decline_friend_request";
    }
}
