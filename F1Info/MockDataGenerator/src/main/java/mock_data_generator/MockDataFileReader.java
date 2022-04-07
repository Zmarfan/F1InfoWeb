package mock_data_generator;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class MockDataFileReader {
    List<String> getSqlFileStatements(final Path path) throws IOException {
        final String fileContent = Files.readString(path);
        final List<String> rawStatements = Arrays.stream(fileContent.trim().split("(?<=;)")).map(String::trim).toList();
        return refineRawStatements(rawStatements);
    }

    private List<String> refineRawStatements(final List<String> rawStatements) {
        boolean doingProcedure = false;

        StringBuilder builder = new StringBuilder();
        final List<String> refinedStatements = new LinkedList<>();
        for (final String rawStatement : rawStatements) {
            if (isProcedure(rawStatement)) {
                builder.append(rawStatement);
                doingProcedure = true;
                continue;
            }
            if (doingProcedure) {
                builder.append(rawStatement);
                if (isEnd(rawStatement)) {
                    refinedStatements.add(builder.toString());
                    builder = new StringBuilder();
                    doingProcedure = false;
                }
            } else {
                refinedStatements.add(rawStatement);
            }
        }
        return refinedStatements;
    }

    private boolean isProcedure(final String rawStatement) {
        return rawStatement.startsWith("create procedure");
    }

    private boolean isEnd(final String rawStatement) {
        return rawStatement.endsWith("end;");
    }
}
