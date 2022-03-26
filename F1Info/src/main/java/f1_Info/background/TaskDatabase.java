package f1_Info.background;

import f1_Info.DatabaseBase;
import f1_Info.configuration.Configuration;
import f1_Info.logger.Logger;

import java.sql.*;

public abstract class TaskDatabase extends DatabaseBase {
    private static final String START_BACKGROUND_JOB_SQL = "insert into background_jobs (name, start_timestamp) values (?,current_timestamp)";
    private static final String STOP_BACKGROUND_JOB_SQL = "update background_jobs set done_timestamp = current_timestamp, error_message = ? where id = ?";

    public TaskDatabase(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public long startBackgroundJob(final String taskName) throws SQLException {
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(START_BACKGROUND_JOB_SQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
                final ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Unable to read and return corresponding id created from background job insert");
            }
        }
    }

    public void stopBackgroundJob(final long backgroundId, final Exception exception) throws SQLException {
        try (final Connection connection = getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(STOP_BACKGROUND_JOB_SQL)) {
                if (exception != null) {
                    preparedStatement.setString(1, exception.toString());
                } else {
                    preparedStatement.setNull(1, Types.VARCHAR);
                }
                preparedStatement.setLong(2, backgroundId);
                preparedStatement.executeUpdate();
            }
        }
    }
}
