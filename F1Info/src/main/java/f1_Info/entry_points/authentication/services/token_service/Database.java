package f1_Info.entry_points.authentication.services.token_service;

import common.configuration.Configuration;
import common.logger.Logger;
import database.DatabaseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Component("TokenServiceDatabase")
public class Database extends DatabaseBase {
    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void insertTokenForUser(final long userId, final UUID token) throws SQLException {
        executeVoidQuery(new InsertTokenForUserQueryData(userId, token.toString()));
    }

    public Optional<TokenRecord> findUserFromToken(final UUID token) throws SQLException {
        return executeOptionalQuery(new FindUserFromTokenQueryData(token.toString()));
    }
}
