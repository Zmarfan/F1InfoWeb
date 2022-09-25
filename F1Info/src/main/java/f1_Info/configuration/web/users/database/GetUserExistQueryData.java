package f1_Info.configuration.web.users.database;

import database.IQueryData;
import lombok.Value;

@Value
public class GetUserExistQueryData implements IQueryData<Boolean> {
    long mUserId;

    @Override
    public String getStoredProcedureName() {
        return "user_details_user_exist";
    }
}
