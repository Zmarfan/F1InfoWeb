package f1_Info.entry_points.authentication.commands.user_login_command;

import lombok.Value;

@Value
public class LoginResponse {
    String mCsrfToken;
}
