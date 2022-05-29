package mock_data_generator;

import database.DatabaseUtil;
import lombok.experimental.UtilityClass;
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

@UtilityClass
public class MockDataGenerator {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/f1database";
    public static final String DATABASE_NAME = "f1User";
    public static final String LOCAL_DATABASE_PASSWORD = "0Vw7LIMZ7K17";

    private static final String RESET_DATABASE_FILE_PATH = "MockDataGenerator/src/main/java/mock_data_generator/setup/reset_database.sql";
    private static final String TABLES_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/tables";
    private static final String DATA_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/data";
    private static final String HELPER_FUNCTIONS_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/helper";

    public static void run() {
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

    private static void resetDatabase() throws SQLException, IOException {
        logInfo("Resetting database...");
        runSqlStatementsFromFilePath(Path.of(RESET_DATABASE_FILE_PATH));
    }

    private static void createTables() throws SQLException, IOException {
        logInfo("Creating Tables...");
        executeSqlFilesInDirectory(TABLES_DIRECTORY);
    }

    private static void createData() throws SQLException, IOException {
        logInfo("Setting up Data...");
        executeSqlFilesInDirectory(DATA_DIRECTORY);
    }

    private static void createHelperFunctions() throws SQLException, IOException {
        logInfo("Setting up Helper Functions...");
        executeSqlFilesInDirectory(HELPER_FUNCTIONS_DIRECTORY);
    }

    private static void createProcedures() throws SQLException, IOException {
        logInfo("Setting up Procedures...");
        executeSqlProcedureFiles();
    }

    private static void createMockData() throws SQLException {
        logInfo("Creating mock data...");

        DatabaseUtil.executeQuery(getConnection(), new CreateUsersQueryData(), null, null);
    }

    private static void executeSqlFilesInDirectory(final String directoryPath) throws SQLException, IOException {
        final File tablesFolder = new File(directoryPath);
        final List<Path> paths = stream(requireNonNull(tablesFolder.listFiles())).map(file -> Path.of(file.getPath())).toList();

        for (final Path path : paths) {
            runSqlStatementsFromFilePath(path);
        }
    }

    private static void executeSqlProcedureFiles() throws SQLException, IOException {
        Path start = Paths.get(".");
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<File> proceduresSqlFiles = stream
                .map(Path::toFile)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith("_procedures.sql"))
                .toList();

            for (final File file : proceduresSqlFiles) {
                runSqlStatementsFromFilePath(file.toPath());
            }
        }
    }

    private static void runSqlStatementsFromFilePath(final Path filePath) throws SQLException, IOException {
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

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_NAME, LOCAL_DATABASE_PASSWORD);
    }
}
