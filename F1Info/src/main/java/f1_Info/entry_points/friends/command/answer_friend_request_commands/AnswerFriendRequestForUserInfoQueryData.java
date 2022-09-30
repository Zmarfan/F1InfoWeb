package f1_Info.entry_points.friends.command.answer_friend_request_commands;

import database.IQueryData;
import lombok.Value;

@Value
public class AnswerFriendRequestForUserInfoQueryData implements IQueryData<AnswerFriendRequestForUserRecord> {
    long m0UserId;
    long m1AnswerUserId;

    @Override
    public String getStoredProcedureName() {
        return "answer_friend_request_for_user_info";
    }
}
