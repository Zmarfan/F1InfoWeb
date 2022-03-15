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
    private static final String RESET_DATABASE_FILE_PATH = "src/main/java/mock_data_generator/setup/reset_database.sql";
    private static final String TABLES_DIRECTORY = "src/main/java/mock_data_generator/tables";
    private static final String DATA_DIRECTORY = "src/main/java/mock_data_generator/data";

    public static void run() {
        try {
            logInfo("Starting Mock Data Generator");
            resetDatabase();
            createTables();
            createData();
        } catch (final Exception e) {
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
        final String url = "jdbc:mysql://localhost:3306/f1database";
        return DriverManager.getConnection(url, "f1User", "0Vw7LIMZ7K17");
    }
}
