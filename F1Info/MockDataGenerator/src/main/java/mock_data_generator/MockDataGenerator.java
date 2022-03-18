package mock_data_generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static mock_data_generator.MockDataLogger.*;

public class MockDataGenerator {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/f1database";
    public static final String DATABASE_NAME = "f1User";
    public static final String LOCAL_DATABASE_PASSWORD = "0Vw7LIMZ7K17";

    private static final String RESET_DATABASE_FILE_PATH = "MockDataGenerator/src/main/java/mock_data_generator/setup/reset_database.sql";
    private static final String TABLES_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/tables";
    private static final String DATA_DIRECTORY = "MockDataGenerator/src/main/java/mock_data_generator/data";

    public static void run() {
        try {
            logInfo("Starting Mock Data Generator");
            resetDatabase();
            createTables();
            createData();
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

    private static void executeSqlFilesInDirectory(final String directoryPath) throws SQLException, IOException {
        final File tablesFolder = new File(directoryPath);
        final List<Path> paths = stream(requireNonNull(tablesFolder.listFiles())).map(file -> Path.of(file.getPath())).toList();

        for (final Path path : paths) {
            runSqlStatementsFromFilePath(path);
        }
    }

    private static void runSqlStatementsFromFilePath(final Path filePath) throws SQLException, IOException {
        logFile("Executing: " + filePath.getFileName().toString());

        final Statement statement = getConnection().createStatement();
        final List<String> sqlStatements = MockDataFileReader.getSqlFileStatements(filePath);
        for (final String sqlStatement : sqlStatements) {
            statement.execute(sqlStatement);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_NAME, LOCAL_DATABASE_PASSWORD);
    }
}
