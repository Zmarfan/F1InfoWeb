package f1_Info.entry_points.development.commands.feedback_commands;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import f1_Info.entry_points.development.commands.feedback_commands.create_feedback_item_command.CreateFeedbackItemQueryData;
import f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command.DeleteFeedbackQueryData;
import f1_Info.entry_points.development.commands.feedback_commands.delete_feedback_item_command.IsUserOwnerOfFeedbackItemQueryData;
import f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command.FeedbackItemRecord;
import f1_Info.entry_points.development.commands.feedback_commands.get_feedback_items_command.GetFeedbackItemsQueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FeedbackCommandsDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public List<FeedbackItemRecord> getFeedbackItems(final long userId) throws SQLException {
        return executeListQuery(new GetFeedbackItemsQueryData(userId));
    }

    public void createFeedbackItem(final long userId, final String text) throws SQLException {
        executeVoidQuery(new CreateFeedbackItemQueryData(userId, text));
    }

    public boolean isUserOwnerOfFeedbackItem(final long userId, final long itemId) throws SQLException {
        return executeBasicQuery(new IsUserOwnerOfFeedbackItemQueryData(userId, itemId));
    }

    public void deleteFeedbackItem(final long itemId) throws SQLException {
        executeVoidQuery(new DeleteFeedbackQueryData(itemId));
    }
}