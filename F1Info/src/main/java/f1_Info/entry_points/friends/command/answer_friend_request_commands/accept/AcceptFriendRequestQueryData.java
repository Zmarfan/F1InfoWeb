package f1_Info.entry_points.friends.command.answer_friend_request_commands.accept;

import database.IQueryData;
import lombok.Value;

@Value
public class AcceptFriendRequestQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1AnswerUserId;

    @Override
    public String getStoredProcedureName() {
        return "accept_friend_request";
    }
}
