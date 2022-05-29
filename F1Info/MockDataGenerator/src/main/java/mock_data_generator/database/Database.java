package mock_data_generator.database;

import common.configuration.Configuration;
import common.logger.Logger;
import database.DatabaseBase;

import java.sql.SQLException;

public class Database extends DatabaseBase {
    public Database(final Configuration configuration, final Logger logger) {
        super(configuration, logger);
    }

    public void createUsers() throws SQLException {
        executeVoidQuery(new CreateUsersQueryData());
    }
}
