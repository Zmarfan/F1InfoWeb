package mock_data_generator;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class MockDataFileReader {
    List<String> getSqlFileStatements(final Path path) throws IOException {
        final String fileContent = Files.readString(path);
        return Arrays.stream(fileContent.trim().split("(?<=;)")).toList();
    }
}
