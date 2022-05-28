package f1_Info.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Email;
import lombok.Value;

@Value
public class ConfigurationRules {
    String mDatabaseUrl;
    String mDatabaseName;
    String mDatabasePassword;
    boolean mIsMock;
    Email mEmail;
    String mEmailPassword;

    public ConfigurationRules(
        @JsonProperty("databaseUrl") final String databaseUrl,
        @JsonProperty("databaseName") final String databaseName,
        @JsonProperty("databasePassword") final String databasePassword,
        @JsonProperty("isMock") final boolean isMock,
        @JsonProperty("email") final String email,
        @JsonProperty("emailPassword") final String emailPassword
    ) {
        mDatabaseUrl = databaseUrl;
        mDatabaseName = databaseName;
        mDatabasePassword = databasePassword;
        mIsMock = isMock;
        mEmail = new Email(email);
        mEmailPassword = emailPassword;
    }
}
