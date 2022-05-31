package f1_Info.configuration.web.users;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import common.logger.Logger;
import f1_Info.configuration.web.users.database.Database;
import f1_Info.configuration.web.users.database.UserDetailsRecord;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class UserManager implements UserDetailsManager {
    private final Database mDatabase;
    private final Logger mLogger;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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

    @Override
    public void createUser(final UserDetails userDetails) throws UnableToRegisterUserException {
        if (userExists(userDetails.getUsername())) {
            throw new UnableToRegisterUserException();
        }

        try {
            final long userId = mDatabase.createUser(userDetails);
            mLogger.info("createUser", this.getClass(), String.format("Created new user: %d, with details: %s", userId, userDetails));
        } catch (final SQLException e) {
            mLogger.severe("createUser", this.getClass(), String.format("Unable to create user for the user details: %s", userDetails), e);
            throw new UnableToRegisterUserException();
        }
    }

    @Override
    public void updateUser(final UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(final String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(final String username) {
        UserDetails existentUserDetails = null;
        try {
            existentUserDetails = loadUserByUsername(username);
        } catch (final UsernameNotFoundException ignore) {
        }

        return existentUserDetails != null;
    }

    private UserDetails createUserDetailsFromRecord(final UserDetailsRecord userDetailsRecord) {
        return new F1UserDetails(
            userDetailsRecord.getUserId(),
            userDetailsRecord.getEmail(),
            userDetailsRecord.getPassword(),
            Authority.fromStringCode(userDetailsRecord.getAuthority()),
            userDetailsRecord.getEnabled()
        );
    }
}
