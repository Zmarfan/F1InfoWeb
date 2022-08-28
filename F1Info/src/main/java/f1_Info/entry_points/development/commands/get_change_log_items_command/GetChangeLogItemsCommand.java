package f1_Info.entry_points.development.commands.get_change_log_items_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetChangeLogItemsCommand implements Command {
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final List<String> items = mDatabase.getChangeLogItems()
            .stream()
            .map(item -> String.format("%s<br><span>%s</span>", item.getItemText(), item.getTime().toString()))
            .toList();
        return ok(new ChangeLogResponse(items));
    }
}