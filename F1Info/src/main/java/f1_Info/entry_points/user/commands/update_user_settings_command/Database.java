package f1_Info.entry_points.user.commands.update_user_settings_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "UpdateUserSettingsCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public CurrentUserSettingsRecord getCurrentUserSettings(final long userId) throws SQLException {
        return executeQuery(new GetCurrentUserSettingsQueryData(userId));
    }

    public void updateSettings(final long userId, final NewUserSettingsRequestBody newUserSettings) throws SQLException {
        executeVoidQuery(new UpdateUserSettingsQueryData(userId, newUserSettings.getDisplayName()));
    }
}