package f1_Info.entry_points.development.commands.feedback_commands.toggle_feedback_like_command;

import f1_Info.entry_points.development.commands.feedback_commands.Database;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class ToggleFeedbackLikeCommand implements Command {
    private final long mUserId;
    private final long mItemId;
    private final boolean mLiked;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        if (mLiked) {
            if (mDatabase.canLikeFeedbackItem(mUserId, mItemId)) {
                mDatabase.likeFeedbackItem(mUserId, mItemId);
            } else {
                return badRequest();
            }
        } else if (mDatabase.canRemoveLikeFromFeedbackItem(mUserId, mItemId)) {
            mDatabase.removeLikeFromFeedbackItem(mUserId, mItemId);
        } else {
            return badRequest();
        }

        return ok();
    }
}
