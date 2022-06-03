package f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service;

import common.helpers.DateFactory;
import common.logger.Logger;
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

    public Optional<Long> findDisabledUserFromToken(final UUID token) {
        try {
            return mDatabase.findDisabledUserFromToken(token)
                .filter(this::tokenIsNotExpired)
                .map(RegistrationTokenRecord::getUserId);
        } catch (final SQLException e) {
            mLogger.severe("findDisabledUserFromToken", this.getClass(), String.format("Unable to find user from token: %s", token), e);
            return Optional.empty();
        }
    }

    private boolean tokenIsNotExpired(final RegistrationTokenRecord registrationTokenRecord) {
        return mDateFactory.nowTime().minus(TIME_TO_ENABLE_ACCOUNT, ChronoUnit.MINUTES).isBefore(registrationTokenRecord.getCreationTime());
    }
}
