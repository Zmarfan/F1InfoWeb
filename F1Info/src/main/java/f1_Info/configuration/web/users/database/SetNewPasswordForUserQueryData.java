package f1_Info.configuration.web.users.database;

import database.IQueryData;
import lombok.Value;

@Value
public class SetNewPasswordForUserQueryData implements IQueryData<Void> {
    long m0UserId;
    String m1NewPassword;

    @Override
    public String getStoredProcedureName() {
        return "user_details_set_new_password_for_user";
    }
}
