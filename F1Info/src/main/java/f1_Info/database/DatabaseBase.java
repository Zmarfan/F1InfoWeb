package f1_Info.database;

import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public abstract class DatabaseBase {
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public <T> T executeQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseRecordsList).get(0);
    }

    public <T> List<T> executeListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseRecordsList);
    }

    public <T> T executeBasicQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseBasicList).get(0);
    }

    public <T> List<T> executeBasicListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseBasicList);
    }

    private <T> List<T> executeAnyQuery(final IQueryData<T> queryData, final Function<SqlParser<T>, List<T>> parseCallback) throws SQLException {
        try (final Connection connection = getConnection()) {
            return DatabaseUtil.executeQuery(connection, queryData, parseCallback, mLogger);
        }
    }

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
