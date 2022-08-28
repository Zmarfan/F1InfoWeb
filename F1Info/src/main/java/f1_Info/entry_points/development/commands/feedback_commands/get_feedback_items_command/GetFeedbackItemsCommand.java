package f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command;

import f1_Info.entry_points.development.commands.feedback_commands.Database;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetFeedbackItemsCommand implements Command {
    private final long mUserId;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(mDatabase.getFeedbackItems(mUserId).stream().map(FeedbackItemResponse::new).toList());
    }
}
