package f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command;

import f1_Info.entry_points.development.commands.feedback_commands.Database;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.*;

@AllArgsConstructor
public class DeleteFeedbackItemCommand implements Command {
    private final long mUserId;
    private final long mItemId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        if (!mDatabase.canDeleteFeedbackItem(mUserId, mItemId)) {
            return forbidden();
        }
        mDatabase.deleteFeedbackItem(mUserId, mItemId);

        return ok();
    }
}
