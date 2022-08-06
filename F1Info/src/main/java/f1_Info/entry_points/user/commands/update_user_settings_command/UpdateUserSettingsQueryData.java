package f1_Info.entry_points.user.commands.update_user_settings_command;

import database.IQueryData;
import lombok.Value;

@Value
public class UpdateUserSettingsQueryData implements IQueryData<Void> {
    long m0UserId;
    String m1DisplayName;

    @Override
    public String getStoredProcedureName() {
        return "update_user_settings_update_settings";
    }
}
