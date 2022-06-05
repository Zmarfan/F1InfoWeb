package f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service;

import database.IQueryData;
import lombok.Value;

@Value
public class InsertRegistrationTokenForUserQueryData implements IQueryData<Void> {
    long m0UserId;
    String m1Token;

    @Override
    public String getStoredProcedureName() {
        return "register_token_service_insert_registration_token";
    }
}
