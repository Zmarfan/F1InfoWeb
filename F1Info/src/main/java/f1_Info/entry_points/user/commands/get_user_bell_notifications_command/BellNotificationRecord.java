package f1_Info.entry_points.user.commands.get_user_bell_notifications_command;

import lombok.Value;

@Value
public class BellNotificationRecord {
    long mNotificationId;
    String mIconType;
    String mTranslationKey;
    boolean mOpened;
    String mParameters;
}
