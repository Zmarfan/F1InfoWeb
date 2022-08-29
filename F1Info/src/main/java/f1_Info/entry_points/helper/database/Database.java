package f1_Info.entry_points.helper.database;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "EndpointHelperDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public boolean isUserManager(final long userId) throws SQLException {
        return executeBasicQuery(new IsUserManagerQueryData(userId));
    }
}