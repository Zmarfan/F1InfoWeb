package f1_Info.services.bell_notification_send_out_service;

import database.IQueryData;
import lombok.Value;

@Value
public class CreateBellNotificationParameterQueryData implements IQueryData<Void> {
    long m0NotificationId;
    String m1Key;
    String m2Value;

    @Override
    public String getStoredProcedureName() {
        return "create_bell_notification_parameter";
    }
}
