package f1_Info.entry_points.friends;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.configuration.web.users.UserManager;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class FriendCodeHandler {
    private static final int MIN_FRIEND_CODE_LENGTH = 10;

    private final Hashids mConverter;
    private final UserManager mUserManager;
    private final Logger mLogger;

    @Autowired
    public FriendCodeHandler(
        final Configuration configuration,
        final UserManager userManager,
        final Logger logger
    ) {
        mConverter = new Hashids("salt", MIN_FRIEND_CODE_LENGTH);
        mUserManager = userManager;
        mLogger = logger;
    }

    public String friendCodeFromUserId(final long userId) {
        return mConverter.encode(userId);
    }

    public Optional<Long> userIdFromFriendCode(final String friendCode) {
        try {
            final long[] decodedIds = mConverter.decode(friendCode);
            if (decodedIds.length == 0) {
                return Optional.empty();
            }
            final long userId = decodedIds[0];
            return mUserManager.userExist(userId) ? Optional.of(userId) : Optional.empty();
        } catch (final SQLException e) {
            mLogger.severe("userIdFromFriendCode", this.getClass(), String.format("Unable to check if user exists from code: %s", friendCode), e);
            return Optional.empty();
        }
    }
}
