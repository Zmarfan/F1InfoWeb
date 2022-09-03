package f1_Info.entry_points.development.manager_commands.feedback_commands;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.CanMarkFeedbackItemAsCompleteQueryData;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.FeedbackAuthorInfoRecord;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.GetFeedbackAuthorInfoQueryData;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_complete_command.MarkFeedbackItemAsCompleteQueryData;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_will_not_do_command.CanMarkItemAsWillNotDo;
import f1_Info.entry_points.development.manager_commands.feedback_commands.mark_feedback_item_as_will_not_do_command.MarkItemAsWillNotDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "MarkFeedbackAsCompleteCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public boolean canMarkFeedbackItemAsComplete(final long itemId) throws SQLException {
        return executeBasicQuery(new CanMarkFeedbackItemAsCompleteQueryData(itemId));
    }

    public void markFeedbackItemAsComplete(final long itemId) throws SQLException {
        executeVoidQuery(new MarkFeedbackItemAsCompleteQueryData(itemId));
    }

    public FeedbackAuthorInfoRecord getFeedbackAuthorInfo(final long itemId) throws SQLException {
        return executeQuery(new GetFeedbackAuthorInfoQueryData(itemId));
    }

    public boolean canMarkItemAsWillNotDo(final long itemId) throws SQLException {
        return executeBasicQuery(new CanMarkItemAsWillNotDo(itemId));
    }

    public void markItemAsWillNotDo(final long itemId) throws SQLException {
        executeVoidQuery(new MarkItemAsWillNotDo(itemId));
    }
}