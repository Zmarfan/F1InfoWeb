package f1_Info.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ConfigurationRules {
    String databaseUrl;
    String databaseName;
    String localDatabasePassword;
    boolean isMock;

    public ConfigurationRules(
        @JsonProperty("databaseUrl") final String databaseUrl,
        @JsonProperty("databaseName") final String databaseName,
        @JsonProperty("localDatabasePassword") final String localDatabasePassword,
        @JsonProperty("isMock") final boolean isMock
    ) {
        this.databaseUrl = databaseUrl;
        this.databaseName = databaseName;
        this.localDatabasePassword = localDatabasePassword;
        this.isMock = isMock;
    }
}
