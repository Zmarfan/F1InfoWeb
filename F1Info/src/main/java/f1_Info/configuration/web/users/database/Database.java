package f1_Info.configuration.web.users.database;

import common.configuration.Configuration;
import common.constants.email.Email;
import common.logger.Logger;
import database.DatabaseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component(value = "UserDetailsDatabase")
public class Database extends DatabaseBase {
    @Autowired
    public Database(Configuration configuration, Logger logger) {
        super(configuration, logger);
    }

    public Optional<UserDetailsRecord> getUserDetailsFromEmail(final Email email) throws SQLException {
        return executeOptionalQuery(new LoadUserByEmailQueryData(email));
    }

    public long createUser(final UserDetails userDetails) throws SQLException {
        return executeBasicQuery(new CreateUserQueryData(userDetails));
    }

    public void enableUser(final long userId) throws SQLException {
        executeVoidQuery(new EnableUserQueryData(userId));
    }

    public void setNewPasswordForUser(final long userId, final String newPassword) throws SQLException {
        executeVoidQuery(new SetNewPasswordForUserQueryData(userId, newPassword));
    }

    public boolean userExist(final long userId) throws SQLException {
        return executeBasicQuery(new GetUserExistQueryData(userId));
    }
}
