package f1_Info.entry_points.authentication.services.token_service;

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
public class TokenService {
    private static final int TIME_TO_ENABLE_ACCOUNT = 30;
    private final Database mDatabase;
    private final DateFactory mDateFactory;
    private final Logger mLogger;

    public void insertTokenForUser(final long userId, final UUID token) {
        try {
            mDatabase.insertTokenForUser(userId, token);
        } catch (final SQLException e) {
            mLogger.severe("insertTokenForUser", this.getClass(), String.format("Unable to insert registration token for user: %d", userId), e);
            throw new UnableToRegisterUserException();
        }
    }

    public Optional<UserInformation> findUserFromToken(final UUID token) {
        try {
            return mDatabase.findUserFromToken(token).map(tokenRecord -> new UserInformation(
                tokenRecord.getUserId(),
                tokenRecord.getEmail(),
                tokenRecord.getPassword(),
                calculateStatusType(tokenRecord)
            ));
        } catch (final SQLException e) {
            mLogger.severe("findUserFromToken", this.getClass(), String.format("Unable to find user from token: %s", token), e);
            return Optional.empty();
        }
    }

    private TokenStatusType calculateStatusType(final TokenRecord tokenRecord) {
        if (tokenRecord.getEnabled()) {
            return TokenStatusType.ALREADY_VERIFIED;
        }
        if (tokenIsExpired(tokenRecord)) {
            return TokenStatusType.TIMED_OUT;
        }
        return TokenStatusType.VALID;
    }

    private boolean tokenIsExpired(final TokenRecord tokenRecord) {
        return !mDateFactory.nowTime().minus(TIME_TO_ENABLE_ACCOUNT, ChronoUnit.MINUTES).isBefore(tokenRecord.getCreationTime());
    }
}
