package database;

import common.configuration.Configuration;
import database.sql_parsing.SqlParser;
import common.logger.Logger;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public abstract class DatabaseBase {
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public <T> T executeQuery(final IQueryData<T> queryData) throws SQLException {
        final List<T> records = executeListQuery(queryData);
        return records.isEmpty() ? null : records.get(0);
    }

    public <T> Optional<T> executeOptionalQuery(final IQueryData<T> queryData) throws SQLException {
        return Optional.ofNullable(executeQuery(queryData));
    }

    public <T> void executeVoidQuery(final IQueryData<T> queryData) throws SQLException {
        executeAnyQuery(queryData, null);
    }

    public <T> List<T> executeListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseRecordsList);
    }

    public <T> T executeBasicQuery(final IQueryData<T> queryData) throws SQLException {
        final List<T> records = executeBasicListQuery(queryData);
        return records.isEmpty() ? null : records.get(0);
    }

    public <T> Optional<T> executeOptionalBasicQuery(final IQueryData<T> queryData) throws SQLException {
        return Optional.ofNullable(executeBasicQuery(queryData));
    }

    public <T> List<T> executeBasicListQuery(final IQueryData<T> queryData) throws SQLException {
        return executeAnyQuery(queryData, SqlParser::parseBasicList);
    }

    public void executeBulkOfWork(final BulkOfWork bulkOfWork) throws SQLException {
        final List<List<IQueryData<Void>>> bulkList = bulkOfWork.getQueryDatas();
        if (bulkList.isEmpty() || bulkList.get(0).isEmpty()) {
            return;
        }
        
        try (final Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (final List<IQueryData<Void>> queryDataList : bulkList) {
                    DatabaseUtil.executeBulkQuery(connection, queryDataList, mLogger);
                }
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                throw e;
            }
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
            try {
                final List<T> result = DatabaseUtil.executeQuery(connection, queryData, parseCallback, mLogger);
                connection.commit();
                return result;
            } catch (final SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
}