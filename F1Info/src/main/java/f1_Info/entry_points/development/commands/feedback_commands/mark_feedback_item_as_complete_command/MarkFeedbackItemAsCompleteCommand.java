package f1_Info.entry_points.development.commands.feedback_commands.mark_feedback_item_as_complete_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class MarkFeedbackItemAsCompleteCommand implements Command {
    private final long mItemId;
    private final MarkFeedbackItemAsCompleteLogic mLogicUnit;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        mLogicUnit.markFeedbackItemAsComplete(mItemId);
        return ok();
    }
}
