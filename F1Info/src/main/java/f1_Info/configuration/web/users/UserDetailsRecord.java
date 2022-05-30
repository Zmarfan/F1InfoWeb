package f1_Info.configuration.web.users;

import common.constants.Email;
import lombok.Value;

@Value
public class UserDetailsRecord {
    long mUserId;
    Email mEmail;
    String mPassword;
    String mAuthority;
    boolean mEnabled;
}
