package common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class Configuration {
    private static final String RULES_LOCATION = "Common/src/main/java/common/configuration/configuration.json";

    private final ObjectMapper mObjectMapper = new ObjectMapper();
    @Getter
    private ConfigurationRules mRules;

    @PostConstruct
    private void readConfigurationJson() throws IOException {
        mRules = mObjectMapper.readValue(new File(RULES_LOCATION), ConfigurationRules.class);
    }
}