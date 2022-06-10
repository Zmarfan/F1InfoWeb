package f1_Info.entry_points.authentication.services.token_service;

import database.IQueryData;
import lombok.Value;

@Value
public class InsertTokenForUserQueryData implements IQueryData<Void> {
    long m0UserId;
    String m1Token;

    @Override
    public String getStoredProcedureName() {
        return "token_service_insert_registration_token";
    }
}
