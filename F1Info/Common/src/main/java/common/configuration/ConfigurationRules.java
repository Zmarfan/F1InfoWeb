package common.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import lombok.Value;

@Value
public class ConfigurationRules {
    String mClientDomain;
    String mDatabaseUrl;
    String mDatabaseName;
    String mDatabasePassword;
    boolean mIsMock;
    Email mEmail;
    String mEmailPassword;
    Email mLoggingEmail;
    String mFriendCodeSalt;

    public ConfigurationRules(
        @JsonProperty("clientDomain") final String clientDomain,
        @JsonProperty("databaseUrl") final String databaseUrl,
        @JsonProperty("databaseName") final String databaseName,
        @JsonProperty("databasePassword") final String databasePassword,
        @JsonProperty("isMock") final boolean isMock,
        @JsonProperty("email") final String email,
        @JsonProperty("emailPassword") final String emailPassword,
        @JsonProperty("loggingEmail") final String loggingEmail,
        @JsonProperty("friendCodeSalt") final String friendCodeSalt
    ) throws MalformedEmailException {
        mClientDomain = clientDomain;
        mDatabaseUrl = databaseUrl;
        mDatabaseName = databaseName;
        mDatabasePassword = databasePassword;
        mIsMock = isMock;
        mEmail = new Email(email);
        mEmailPassword = emailPassword;
        mLoggingEmail = new Email(loggingEmail);
        mFriendCodeSalt = friendCodeSalt;
    }
}
