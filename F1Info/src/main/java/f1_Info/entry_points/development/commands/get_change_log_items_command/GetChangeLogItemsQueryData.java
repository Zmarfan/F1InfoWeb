package f1_Info.entry_points.development.commands.get_change_log_items_command;

import database.IQueryData;

public class GetChangeLogItemsQueryData implements IQueryData<ChangeLogItemRecord> {
    @Override
    public String getStoredProcedureName() {
        return "get_change_log_items";
    }
}
