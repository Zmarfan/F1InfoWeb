package f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler;

import common.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class FailedLoginHandler {
    private static final int MAX_FAILED_ATTEMPTS = 5;

    private final Database mDatabase;
    private final Logger mLogger;

    public boolean canMakeAnotherLoginAttempt(final String ip) {
        try {
            mDatabase.resetAmountOfFailedAttemptsIfWaitedLongEnough(ip);
            final int failedAttempts = mDatabase.getAmountOfFailedAttempts(ip);
            return failedAttempts <= MAX_FAILED_ATTEMPTS;
        } catch (final SQLException e) {
            mLogger.severe("canMakeAnotherLoginAttempt", this.getClass(), String.format("Unable to verify if ip attempting to login is allowed: %s", ip), e);
            return false;
        }
    }

    public void addFailedLoginAttempt(final String ip) {
        try {
            mDatabase.addFailedLoginAttempt(ip);
        } catch (final SQLException e) {
            mLogger.severe("addFailedLoginAttempt", this.getClass(), String.format("Unable to add failed login attempt for ip: %s", ip), e);
        }
    }

    public void resetFailedAttempts(final String ip) {
        try {
            mDatabase.resetFailedAttempts(ip);
        } catch (final SQLException e) {
            mLogger.severe("resetFailedAttempts", this.getClass(), String.format("Unable to reset failed login attempts for ip: %s", ip), e);
        }
    }
}
