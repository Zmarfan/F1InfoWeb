package f1_Info.entry_points.development.commands.get_change_log_items_command;

import lombok.Value;

import java.util.List;

@Value
public class ChangeLogResponse {
    List<String> mItems;
}
