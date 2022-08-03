package f1_Info.entry_points.authentication.commands.get_user_command;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "GetUserCommandDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public GetUserRecord getUser(final long userId) throws SQLException {
        return executeQuery(new GetUserQueryData(userId));
    }
}
