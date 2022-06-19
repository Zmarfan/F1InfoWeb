package common.configuration;

import common.constants.email.MalformedEmailException;

public class ConfigurationRulesTestBuilder {
    String mClientDomain = null;
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

    public ConfigurationRules build() throws MalformedEmailException {
        return new ConfigurationRules(mClientDomain, mDatabaseUrl, mDatabaseName, mDatabasePassword, mIsMock, mEmail, mEmailPassword, mLoggingEmail);
    }
}
