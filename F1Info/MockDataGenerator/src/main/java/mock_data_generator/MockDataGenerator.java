package mock_data_generator;

import common.configuration.ConfigurationRules;
import database.DatabaseUtil;
import lombok.AllArgsConstructor;
import mock_data_generator.database.CreateUsersQueryData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static mock_data_generator.ConsoleLogger.*;

@AllArgsConstructor
public class MockDataGenerator {
    private static final String RESET_DATABASE_FILE_PATH = "MockDataGenerator/src/main/java/mock_data_generator/setup/reset_database.sql";
    private static final String SYSTEM_DATA_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/data";
    private static final String F1_DATA_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/f1_data";
    private static final String HELPER_FUNCTIONS_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/helper";

    private final ConfigurationRules mConfigRules;

    public void runExtensive() {
        try {
            final Connection connection = getConnection();

            logInfo("Starting Extensive Mock Data Generator");
            resetDatabase(connection);
            createTables(connection);
            createHelperFunctions(connection);
            createSystemData(connection);
            createF1Data(connection);
            createProcedures(connection);
            createMockData();
            logSuccess("Successfully reset local database!");
        } catch (final Exception e) {
            logError("Unable to finish the extensive mock data generation!");
            logError(e.toString());
        }
    }

    public void runMinimalistic() {
        try {
            final Connection connection = getConnection();

            logInfo("Starting Minimalistic Mock Data Generator");
            resetDatabase(connection);
            createTables(connection);
            createHelperFunctions(connection);
            createSystemData(connection);
            createProcedures(connection);
            createMockData();
            logSuccess("Successfully reset local database!");
        } catch (final Exception e) {
            logError("Unable to finish the minimalistic mock data generation!");
            logError(e.toString());
        }
    }

    private void resetDatabase(final Connection connection) throws SQLException, IOException {
        logInfo("Resetting database...");
        runSqlStatementsFromFilePath(connection, Path.of(RESET_DATABASE_FILE_PATH));
    }

    private void createTables(final Connection connection) throws SQLException, IOException {
        logInfo("Creating Tables...");
        runSqlFilesEndingWith(connection, "_tables.sql");
    }

    private void createSystemData(final Connection connection) throws SQLException, IOException {
        logInfo("Setting up System Data...");
        executeSqlFilesInDirectory(connection, SYSTEM_DATA_DIRECTORY);
    }

    private void createF1Data(final Connection connection) throws SQLException, IOException {
        logInfo("Setting up F1 Data...");
        executeSqlFilesInDirectory(connection, F1_DATA_DIRECTORY);
    }

    private void createHelperFunctions(final Connection connection) throws SQLException, IOException {
        logInfo("Setting up Helper Functions...");
        executeSqlFilesInDirectory(connection, HELPER_FUNCTIONS_DIRECTORY);
    }

    private void createProcedures(final Connection connection) throws SQLException, IOException {
        logInfo("Setting up Procedures...");
        runSqlFilesEndingWith(connection, "_procedures.sql");
    }

    private void createMockData() throws SQLException {
        logInfo("Creating mock data...");

        DatabaseUtil.executeQuery(getConnection(), new CreateUsersQueryData(), null, null);
    }

    private void executeSqlFilesInDirectory(final Connection connection, final String directoryPath) throws SQLException, IOException {
        final File tablesFolder = new File(directoryPath);
        final List<Path> paths = stream(requireNonNull(tablesFolder.listFiles())).map(file -> Path.of(file.getPath())).toList();

        for (final Path path : paths) {
            runSqlStatementsFromFilePath(connection, path);
        }
    }

    private void runSqlStatementsFromFilePath(final Connection connection, final Path filePath) throws SQLException, IOException {
        logFile("Executing: " + filePath.getFileName().toString());

        final List<String> sqlStatements = MockDataFileReader.getSqlFileStatements(filePath);
        String executingStatement = sqlStatements.get(0);
        try (final Statement statement = connection.createStatement()) {
            for (final String sqlStatement : sqlStatements) {
                executingStatement = sqlStatement;
                statement.execute(sqlStatement);
            }
        }
        catch (final Exception e) {
            logError("statement: " + executingStatement);
            throw e;
        }
    }

    private void runSqlFilesEndingWith(final Connection connection, final String ending) throws SQLException, IOException {
        Path start = Paths.get(".");
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<File> proceduresSqlFiles = stream
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(ending))
                .toList();

            for (final File file : proceduresSqlFiles) {
                runSqlStatementsFromFilePath(connection, file.toPath());
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(mConfigRules.getDatabaseUrl(), mConfigRules.getDatabaseName(), mConfigRules.getDatabasePassword());
    }
}
