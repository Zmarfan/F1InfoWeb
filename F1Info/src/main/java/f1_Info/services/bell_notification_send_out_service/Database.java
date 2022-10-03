package f1_Info.services.bell_notification_send_out_service;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "BellNotificationSendOutServiceDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public long createBellNotification(final CreateNotificationParameters parameters) throws SQLException {
        return executeBasicQuery(new CreateBellNotificationQueryData(
            parameters.getUserId(),
            parameters.getIcon().getCode(),
            parameters.getClickType().getCode(),
            parameters.getKey()
        ));
    }

    public void createBellNotificationParameters(final List<CreateBellNotificationParameterQueryData> parameterQueryDataList) throws SQLException {
        executeBulkOfWork(new BulkOfWork(parameterQueryDataList));
    }
}