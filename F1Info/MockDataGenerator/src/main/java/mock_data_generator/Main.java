package mock_data_generator;

import common.configuration.Configuration;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        final Configuration configuration = new Configuration();
        configuration.readConfigurationJson();
        new MockDataGenerator(configuration.getRules()).run();
    }
}
