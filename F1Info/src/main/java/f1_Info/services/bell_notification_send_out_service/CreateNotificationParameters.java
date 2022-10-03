package f1_Info.services.bell_notification_send_out_service;

import lombok.Value;

import java.util.Map;

@Value
public class CreateNotificationParameters {
    long mUserId;
    BellNotificationIcon mIcon;
    BellNotificationClickType mClickType;
    String mKey;
    Map<String, String> mParameters;
}
