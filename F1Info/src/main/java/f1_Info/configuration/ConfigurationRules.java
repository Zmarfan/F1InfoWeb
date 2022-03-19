package f1_Info.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ConfigurationRules {
    boolean isMock;

    public ConfigurationRules(@JsonProperty("isMock") final boolean isMock) {
        this.isMock = isMock;
    }
}
