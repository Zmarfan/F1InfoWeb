package f1_Info.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ConfigurationRules {
    String mDatabaseUrl;
    String mDatabaseName;
    String mDatabasePassword;
    boolean mIsMock;

    public ConfigurationRules(
        @JsonProperty("databaseUrl") final String databaseUrl,
        @JsonProperty("databaseName") final String databaseName,
        @JsonProperty("databasePassword") final String databasePassword,
        @JsonProperty("isMock") final boolean isMock
    ) {
        mDatabaseUrl = databaseUrl;
        mDatabaseName = databaseName;
        mDatabasePassword = databasePassword;
        mIsMock = isMock;
    }
}
