package common.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import lombok.Value;

@Value
public class ConfigurationRules {
    String mDatabaseUrl;
    String mDatabaseName;
    String mDatabasePassword;
    boolean mIsMock;
    Email mEmail;
    String mEmailPassword;
    Email mLoggingEmail;

    public ConfigurationRules(
        @JsonProperty("databaseUrl") final String databaseUrl,
        @JsonProperty("databaseName") final String databaseName,
        @JsonProperty("databasePassword") final String databasePassword,
        @JsonProperty("isMock") final boolean isMock,
        @JsonProperty("email") final String email,
        @JsonProperty("emailPassword") final String emailPassword,
        @JsonProperty("loggingEmail") final String loggingEmail
    ) throws MalformedEmailException {
        mDatabaseUrl = databaseUrl;
        mDatabaseName = databaseName;
        mDatabasePassword = databasePassword;
        mIsMock = isMock;
        mEmail = new Email(email);
        mEmailPassword = emailPassword;
        mLoggingEmail = new Email(loggingEmail);
    }
}
