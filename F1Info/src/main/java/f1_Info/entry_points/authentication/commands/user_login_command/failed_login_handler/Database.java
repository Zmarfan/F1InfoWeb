package f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler;

import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component(value = "FailedLoginHandlerDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void resetAmountOfFailedAttemptsIfWaitedLongEnough(final String ip) throws SQLException {
        executeVoidQuery(new ResetFailAmountIfNeededQueryData(ip));
    }

    public int getAmountOfFailedAttempts(final String ip) throws SQLException {
        return executeBasicQuery(new GetAmountOfFailedRequestsQueryData(ip));
    }
}