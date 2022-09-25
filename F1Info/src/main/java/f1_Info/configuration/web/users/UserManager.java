package f1_Info.configuration.web.users;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.database.Database;
import f1_Info.configuration.web.users.database.UserDetailsRecord;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class UserManager implements UserDetailsService {
    private final Database mDatabase;
    private final Logger mLogger;

    @Override
    public F1UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            final Optional<UserDetailsRecord> userDetailsRecord = mDatabase.getUserDetailsFromEmail(new Email(email));
            if (userDetailsRecord.isEmpty()) {
                throw new UsernameNotFoundException(String.format("The provided email: %s does not exist", email));
            }

            return createUserDetailsFromRecord(userDetailsRecord.get());
        } catch (final SQLException | MalformedEmailException e) {
            final String errorMessage = String.format("Unable to fetch user details for email: %s", email);
            mLogger.severe("loadUserByUsername", this.getClass(), errorMessage, e);
            throw new UsernameNotFoundException(errorMessage);
        }
    }

    public long registerUser(final F1UserDetails userDetails) throws UnableToRegisterUserException {
        try {
            final Optional<UserDetailsRecord> existentUserDetails = mDatabase.getUserDetailsFromEmail(userDetails.getEmail());
            if (existentUserDetails.isPresent() && !existentUserDetails.get().getEnabled()) {
                return existentUserDetails.get().getUserId();
            }
            if (existentUserDetails.isPresent()) {
                throw new UnableToRegisterUserException();
            }

            final long userId = mDatabase.createUser(userDetails);
            mLogger.info("createUser", this.getClass(), String.format("Created new user: %d, with details: %s", userId, userDetails));
            return userId;
        } catch (final SQLException e) {
            mLogger.severe("createUser", this.getClass(), String.format("Unable to create user for the user details: %s", userDetails), e);
            throw new UnableToRegisterUserException();
        }
    }

    public void enableUser(final long userId) throws UnableToRegisterUserException {
        try {
            mDatabase.enableUser(userId);
        } catch (final SQLException e) {
            mLogger.severe("enableUser", this.getClass(), String.format("Unable to enable user: %d", userId), e);
            throw new UnableToRegisterUserException(e);
        }
    }

    public void setNewPasswordForUser(final long userId, final String newPassword) throws UnableToRegisterUserException {
        try {
            mDatabase.setNewPasswordForUser(userId, newPassword);
        } catch (final SQLException e) {
            mLogger.severe("setNewPasswordForUser", this.getClass(), String.format("Unable to set new password for user %d", userId), e);
            throw new UnableToRegisterUserException(e);
        }
    }

    public boolean userExist(final long userId) throws SQLException {
        return mDatabase.userExist(userId);
    }

    private F1UserDetails createUserDetailsFromRecord(final UserDetailsRecord userDetailsRecord) {
        return new F1UserDetails(
            userDetailsRecord.getUserId(),
            userDetailsRecord.getEmail(),
            userDetailsRecord.getPassword(),
            Authority.fromStringCode(userDetailsRecord.getAuthority()),
            userDetailsRecord.getEnabled()
        );
    }
}
