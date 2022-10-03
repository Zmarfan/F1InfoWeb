package f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command;

import lombok.Value;

@Value
public class BellNotificationRecord {
    long mNotificationId;
    String mIconType;
    String mClickType;
    String mTranslationKey;
    boolean mOpened;
    String mParameters;
}
