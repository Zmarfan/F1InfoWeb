package f1_Info.entry_points.authentication.services.register_token_service;

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
