package f1_Info.entry_points.user.commands.bell_notification_commands.mark_bell_notifications_as_opened_command;

import database.IQueryData;
import lombok.Value;

@Value
public class MarkBellNotificationsAsOpenedQueryData implements IQueryData<Void> {
    long m0UserId;
    long m1NotificationId;

    @Override
    public String getStoredProcedureName() {
        return "mark_bell_notifications_as_opened";
    }
}
