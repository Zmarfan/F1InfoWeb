package f1_Info.entry_points.authentication.services.register_token_service;

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
