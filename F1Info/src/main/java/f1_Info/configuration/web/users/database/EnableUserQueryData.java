package f1_Info.configuration.web.users.database;

import database.IQueryData;
import lombok.Value;

@Value
public class EnableUserQueryData implements IQueryData<Void> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "user_details_enable_user";
    }
}
