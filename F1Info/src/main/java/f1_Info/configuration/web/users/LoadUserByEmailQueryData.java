package f1_Info.configuration.web.users;

import common.constants.email.Email;
import database.IQueryData;
import lombok.Value;

@Value
public class LoadUserByEmailQueryData implements IQueryData<UserDetailsRecord> {
    Email mEmail;

    @Override
    public String getStoredProcedureName() {
        return "user_details_load_user_by_email";
    }
}
