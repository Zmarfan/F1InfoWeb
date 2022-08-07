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
    private static final String DATA_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/data";
    private static final String HELPER_FUNCTIONS_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/helper";

    private final ConfigurationRules mConfigRules;

    public void run() {
        try {
            logInfo("Starting Mock Data Generator");
            resetDatabase();
            createTables();
            createHelperFunctions();
            createData();
            createProcedures();
            createMockData();
            logSuccess("Successfully reset local database!");
        } catch (final Exception e) {
            logError("Unable to finish the mock data generation!");
            logError(e.toString());
        }
    }

    private void resetDatabase() throws SQLException, IOException {
        logInfo("Resetting database...");
        runSqlStatementsFromFilePath(Path.of(RESET_DATABASE_FILE_PATH));
    }

    private void createTables() throws SQLException, IOException {
        logInfo("Creating Tables...");
        runSqlFilesEndingWith("_tables.sql");
    }

    private void createData() throws SQLException, IOException {
        logInfo("Setting up Data...");
        executeSqlFilesInDirectory(DATA_DIRECTORY);
    }

    private void createHelperFunctions() throws SQLException, IOException {
        logInfo("Setting up Helper Functions...");
        executeSqlFilesInDirectory(HELPER_FUNCTIONS_DIRECTORY);
    }

    private void createProcedures() throws SQLException, IOException {
        logInfo("Setting up Procedures...");
        runSqlFilesEndingWith("_procedures.sql");
    }

    private void createMockData() throws SQLException {
        logInfo("Creating mock data...");

        DatabaseUtil.executeQuery(getConnection(), new CreateUsersQueryData(), null, null);
    }

    private void executeSqlFilesInDirectory(final String directoryPath) throws SQLException, IOException {
        final File tablesFolder = new File(directoryPath);
        final List<Path> paths = stream(requireNonNull(tablesFolder.listFiles())).map(file -> Path.of(file.getPath())).toList();

        for (final Path path : paths) {
            runSqlStatementsFromFilePath(path);
        }
    }

    private void runSqlStatementsFromFilePath(final Path filePath) throws SQLException, IOException {
        logFile("Executing: " + filePath.getFileName().toString());

        final List<String> sqlStatements = MockDataFileReader.getSqlFileStatements(filePath);
        String executingStatement = sqlStatements.get(0);
        try (final Statement statement = getConnection().createStatement()) {
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

    private void runSqlFilesEndingWith(final String ending) throws SQLException, IOException {
        Path start = Paths.get(".");
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<File> proceduresSqlFiles = stream
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(ending))
                .toList();

            for (final File file : proceduresSqlFiles) {
                runSqlStatementsFromFilePath(file.toPath());
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(mConfigRules.getDatabaseUrl(), mConfigRules.getDatabaseName(), mConfigRules.getDatabasePassword());
    }
}
