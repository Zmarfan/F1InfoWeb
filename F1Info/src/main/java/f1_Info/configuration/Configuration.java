package f1_Info.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class Configuration {
    private static final String RULES_LOCATION = "src/main/java/f1_Info/configuration/configuration.json";

    final Logger mLogger;
    final ObjectMapper mObjectMapper;
    ConfigurationRules mRules;

    @Autowired
    public Configuration(Logger mLogger) {
        this.mLogger = mLogger;
        mObjectMapper = new ObjectMapper();
    }

    public ConfigurationRules getRules() {
        return mRules;
    }

    @PostConstruct
    private void readConfigurationJson() throws IOException {
        try {
            mRules = mObjectMapper.readValue(new File(RULES_LOCATION), ConfigurationRules.class);
        } catch (final Exception e) {
            mLogger.logError("Unable to fetch configuration json", e);
            throw e;
        }
    }
}
