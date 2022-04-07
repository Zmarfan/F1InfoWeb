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
        final List<T> records = executeListQuery(queryData);
        return records.isEmpty() ? null : records.get(0);
    }

    public <T> List<T> executeListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseRecordsList);
    }

    public <T> T executeBasicQuery(final IQueryData<T> queryData) throws SQLException {
        final List<T> records = executeBasicListQuery(queryData);
        return records.isEmpty() ? null : records.get(0);
    }

    public <T> List<T> executeBasicListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseBasicList);
    }

    public void executeBulkVoidQueries(final DatabaseBulkOfWork bulkOfWork) throws SQLException {
        try (final Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            for (final IQueryData<Void> queryData : bulkOfWork.getQueryDatas()) {
                DatabaseUtil.executeQuery(connection, queryData, null, mLogger);
            }
            connection.commit();
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

    private <T> List<T> executeAnyQuery(final IQueryData<T> queryData, final Function<SqlParser<T>, List<T>> parseCallback) throws SQLException {
        try (final Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            final List<T> result = DatabaseUtil.executeQuery(connection, queryData, parseCallback, mLogger);
            connection.commit();
            return result;
        }
    }
}
