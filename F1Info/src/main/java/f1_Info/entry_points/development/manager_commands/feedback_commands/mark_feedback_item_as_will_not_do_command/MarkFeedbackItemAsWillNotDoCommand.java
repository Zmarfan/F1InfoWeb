package f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_will_not_do_command;

import f1_Info.entry_points.development.manager_commands.feedback_commands.Database;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.badRequest;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class MarkFeedbackItemAsWillNotDoCommand implements Command {
    private final long mUserId;
    private final long mItemId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        if (!mDatabase.canMarkItemAsWillNotDo(mItemId)) {
            return badRequest();
        }
        mDatabase.markItemAsWillNotDo(mUserId, mItemId);
        return ok();
    }
}
