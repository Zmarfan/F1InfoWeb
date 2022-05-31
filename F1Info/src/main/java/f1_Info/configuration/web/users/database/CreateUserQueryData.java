package f1_Info.configuration.web.users.database;

import database.IQueryData;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@Value
public class CreateUserQueryData implements IQueryData<Long> {
    String m1Email;
    String m2Password;
    String m3Authority;
    boolean m4Enabled;

    public CreateUserQueryData(final UserDetails userDetails) {
        m1Email = userDetails.getUsername();
        m2Password = userDetails.getPassword();
        m3Authority = new ArrayList<>(userDetails.getAuthorities()).get(0).getAuthority();
        m4Enabled = userDetails.isEnabled();
    }

    @Override
    public String getStoredProcedureName() {
        return "user_details_create_user";
    }
}
