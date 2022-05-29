package f1_Info.configuration;

public class ConfigurationRulesBuilder {
    String mDatabaseUrl = null;
    String mDatabaseName = null;
    String mDatabasePassword = null;
    final boolean mIsMock;
    String mEmail = "test@test.com";
    String mEmailPassword = null;
    String mLoggingEmail = "test@test.com";

    public ConfigurationRulesBuilder(boolean isMock) {
        mIsMock = isMock;
    }

    public static ConfigurationRulesBuilder builder(final boolean isMock) {
        return new ConfigurationRulesBuilder(isMock);
    }

    public ConfigurationRules build() {
        return new ConfigurationRules(mDatabaseUrl, mDatabaseName, mDatabasePassword, mIsMock, mEmail, mEmailPassword, mLoggingEmail);
    }
}
