package f1_Info.background;

import f1_Info.configuration.Configuration;
import f1_Info.configuration.ConfigurationRules;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class Database {
    private static final String SQL_STATEMENT = "insert into constructors (constructor_identifier, name, country_code, wikipedia_page) values (?,?,?,?) on duplicate key update id = id;";

    private final ConfigurationRules mConfigurationRules;
    private final Logger mLogger;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        this.mConfigurationRules = configuration.getRules();
        this.mLogger = logger;
    }

    public void mergeIntoConstructorsData(final List<ConstructorData> constructorDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final ConstructorData constructorData : constructorDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(SQL_STATEMENT)) {
                    preparedStatement.setString(1, constructorData.getConstructorIdentifier());
                    preparedStatement.setString(2, constructorData.getName());
                    preparedStatement.setString(3, constructorData.getCountry().getCode());
                    preparedStatement.setString(4, constructorData.getWikipediaUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(
                mConfigurationRules.getDatabaseUrl(),
                mConfigurationRules.getDatabaseName(),
                mConfigurationRules.getDatabasePassword()
            );
        } catch (final SQLException e) {
            mLogger.logError("getConnection", Database.class, "Unable to establish connection with database", e);
            throw e;
        }
    }
}
