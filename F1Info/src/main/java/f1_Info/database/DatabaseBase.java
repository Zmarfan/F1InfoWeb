package f1_Info.database;

import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public abstract class DatabaseBase {
    private final Configuration mConfiguration;
    private final Logger mLogger;

    protected Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(
                mConfiguration.getRules().getDatabaseUrl(),
                mConfiguration.getRules().getDatabaseName(),
                mConfiguration.getRules().getDatabasePassword()
            );
        } catch (final SQLException e) {
            mLogger.severe("getConnection", DatabaseBase.class, "Unable to establish connection with database", e);
            throw e;
        }
    }
}
