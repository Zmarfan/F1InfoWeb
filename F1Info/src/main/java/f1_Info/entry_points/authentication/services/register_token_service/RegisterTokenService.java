package f1_Info.entry_points.authentication.services.register_token_service;

import common.helpers.DateFactory;
import common.logger.Logger;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class RegisterTokenService {
    private static final int TIME_TO_ENABLE_ACCOUNT = 30;
    private final Database mDatabase;
    private final DateFactory mDateFactory;
    private final Logger mLogger;

    public void insertRegistrationTokenForUser(final long userId, final UUID token) {
        try {
            mDatabase.insertRegistrationTokenForUser(userId, token);
        } catch (final SQLException e) {
            mLogger.severe("insertRegistrationTokenForUser", this.getClass(), String.format("Unable to insert registration token for user: %d", userId), e);
            throw new UnableToRegisterUserException();
        }
    }

    public Optional<UserInformation> findUserFromToken(final UUID token) {
        try {
            return mDatabase.findUserFromToken(token).map(tokenRecord -> new UserInformation(
                tokenRecord.getUserId(),
                tokenRecord.getEmail(),
                calculateStatusType(tokenRecord)
            ));
        } catch (final SQLException e) {
            mLogger.severe("findDisabledUserFromToken", this.getClass(), String.format("Unable to find user from token: %s", token), e);
            return Optional.empty();
        }
    }

    private RegisterTokenStatusType calculateStatusType(final RegistrationTokenRecord tokenRecord) {
        if (tokenRecord.getEnabled()) {
            return RegisterTokenStatusType.ALREADY_VERIFIED;
        }
        if (tokenIsExpired(tokenRecord)) {
            return RegisterTokenStatusType.TIMED_OUT;
        }
        return RegisterTokenStatusType.VALID;
    }

    private boolean tokenIsExpired(final RegistrationTokenRecord registrationTokenRecord) {
        return !mDateFactory.nowTime().minus(TIME_TO_ENABLE_ACCOUNT, ChronoUnit.MINUTES).isBefore(registrationTokenRecord.getCreationTime());
    }
}
