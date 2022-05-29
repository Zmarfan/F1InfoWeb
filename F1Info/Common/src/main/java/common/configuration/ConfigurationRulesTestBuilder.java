package common.configuration;

public class ConfigurationRulesTestBuilder {
    String mDatabaseUrl = null;
    String mDatabaseName = null;
    String mDatabasePassword = null;
    final boolean mIsMock;
    String mEmail = "test@test.com";
    String mEmailPassword = null;
    String mLoggingEmail = "test@test.com";

    public ConfigurationRulesTestBuilder(boolean isMock) {
        mIsMock = isMock;
    }

    public static ConfigurationRulesTestBuilder builder(final boolean isMock) {
        return new ConfigurationRulesTestBuilder(isMock);
    }

    public ConfigurationRules build() {
        return new ConfigurationRules(mDatabaseUrl, mDatabaseName, mDatabasePassword, mIsMock, mEmail, mEmailPassword, mLoggingEmail);
    }
}
