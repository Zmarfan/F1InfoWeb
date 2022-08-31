package f1_Info.entry_points.user.commands.bell_notification_commands.get_user_bell_notifications_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetBellNotificationsToDisplayQueryData implements IQueryData<BellNotificationRecord> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "get_user_bell_notifications_to_display";
    }
}
