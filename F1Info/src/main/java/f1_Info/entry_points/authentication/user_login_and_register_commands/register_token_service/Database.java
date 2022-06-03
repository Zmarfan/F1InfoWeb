package f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service;

import common.configuration.Configuration;
import common.logger.Logger;
import database.DatabaseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Component("RegisterTokenServiceDatabase")
public class Database extends DatabaseBase {
    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<RegistrationTokenRecord> findDisabledUserFromToken(final UUID token) throws SQLException {
        return executeOptionalQuery(new FindDisabledUserFromTokenQueryData(token.toString()));
    }
}