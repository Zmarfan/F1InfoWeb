package f1_Info.entry_points.change_log.commands.get_change_log_items_command;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ChangeLogItemRecord {
    String mItemText;
    LocalDateTime mTime;
}
