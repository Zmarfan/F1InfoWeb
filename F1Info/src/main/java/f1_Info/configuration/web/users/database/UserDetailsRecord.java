package f1_Info.configuration.web.users.database;

import common.constants.email.Email;
import lombok.Value;

@Value
public class UserDetailsRecord {
    long mUserId;
    Email mEmail;
    String mPassword;
    String mAuthority;
    boolean mEnabled;
}
