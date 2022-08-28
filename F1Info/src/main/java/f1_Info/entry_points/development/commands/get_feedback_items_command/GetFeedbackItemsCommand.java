package f1_Info.entry_points.development.commands.get_feedback_items_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetFeedbackItemsCommand implements Command {
    @Override
    public ResponseEntity<?> execute() throws SQLException {
        return ok(List.of(
            new FeedbackItemResponse(1, "Lorem ipsum dolor sit amet, consectetur adipisicing elit.", "User1", LocalDate.now().toString(), 0, false, false, false),
            new FeedbackItemResponse(2, "Lorem ipsum, consectetur adipisicing elit.", "User2", LocalDate.now().toString(), 5, false, false, false),
            new FeedbackItemResponse(3, "Lorem ipsum dolor sit amet, lit.", "User3", LocalDate.now().toString(), 4, false, true, false),
            new FeedbackItemResponse(4, "Lorem adipisicing elit.", "User4", LocalDate.now().toString(), 3, false, false, true),
            new FeedbackItemResponse(4, "Lorem adipisicasing elit.", "User5", LocalDate.now().toString(), 3, true, true, true),
            new FeedbackItemResponse(4, "Lorem adidpisicing elit.", "User5", LocalDate.now().toString(), 3, true, true, true)
        ));
    }
}
