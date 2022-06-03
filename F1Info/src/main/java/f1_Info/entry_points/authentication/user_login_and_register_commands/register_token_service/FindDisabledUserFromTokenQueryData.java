package f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service;

import database.IQueryData;
import lombok.Value;

@Value
public class FindDisabledUserFromTokenQueryData implements IQueryData<RegistrationTokenRecord> {
    String mToken;

    @Override
    public String getStoredProcedureName() {
        return "register_token_service_find_user_from_token";
    }
}
