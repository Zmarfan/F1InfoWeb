package mock_data_generator;

import common.configuration.Configuration;
import common.configuration.ConfigurationRules;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class Main {
    public static void main(final String[] args) throws IOException {
        final MockDataGenerator mockDataGenerator = new MockDataGenerator(getConfigurationRules());
        if (Objects.equals(args[0], "Y")) {
            mockDataGenerator.runExtensive();
        } else {
            mockDataGenerator.runMinimalistic();
        }
    }

    private static ConfigurationRules getConfigurationRules() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.readConfigurationJson();
        return configuration.getRules();
    }
}
