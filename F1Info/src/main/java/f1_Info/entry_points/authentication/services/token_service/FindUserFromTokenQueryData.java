package f1_Info.entry_points.authentication.services.token_service;

import database.IQueryData;
import lombok.Value;

@Value
public class FindUserFromTokenQueryData implements IQueryData<TokenRecord> {
    String mToken;

    @Override
    public String getStoredProcedureName() {
        return "token_service_find_user_from_token";
    }
}
