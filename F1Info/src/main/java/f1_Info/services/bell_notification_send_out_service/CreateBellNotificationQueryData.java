package f1_Info.services.bell_notification_send_out_service;

import database.IQueryData;
import lombok.Value;

@Value
public class CreateBellNotificationQueryData implements IQueryData<Long> {
    long m0UserId;
    String m1IconCode;
    String m2Key;

    @Override
    public String getStoredProcedureName() {
        return "create_bell_notification";
    }
}
