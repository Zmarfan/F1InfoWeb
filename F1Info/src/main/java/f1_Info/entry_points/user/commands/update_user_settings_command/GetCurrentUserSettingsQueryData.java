package f1_Info.entry_points.user.commands.update_user_settings_command;

import database.IQueryData;
import lombok.Value;

@Value
public class GetCurrentUserSettingsQueryData implements IQueryData<CurrentUserSettingsRecord> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "update_user_settings_get_current_settings";
    }
}
