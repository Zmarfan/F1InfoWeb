package f1_Info.services.bell_notification_send_out_service;

import common.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class BellNotificationSendOutService {
    private final Database mDatabase;
    private final Logger mLogger;

    public void sendBellNotification(final CreateNotificationParameters parameters) {
        try {
            createBellNotification(parameters);
        } catch (final SQLException e) {
            mLogger.severe("sendBellNotification", this.getClass(), String.format("Unable to create a bell notification with params: %s", parameters.toString()), e);
        }
    }

    private void createBellNotification(final CreateNotificationParameters parameters) throws SQLException {
        final long notificationId = mDatabase.createBellNotification(parameters);

        final List<CreateBellNotificationParameterQueryData> parameterQueryDataList = parameters.getParameters()
            .entrySet()
            .stream()
            .map(entry -> new CreateBellNotificationParameterQueryData(notificationId, entry.getKey(), entry.getValue()))
            .toList();
        if (!parameterQueryDataList.isEmpty()) {
            mDatabase.createBellNotificationParameters(parameterQueryDataList);
        }
    }
}
