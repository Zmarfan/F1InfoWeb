package f1_Info.entry_points.helper.database;

import database.IQueryData;
import lombok.Value;

@Value
public class IsUserManagerQueryData implements IQueryData<Boolean> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "endpoint_helper_is_user_manager";
    }
}
