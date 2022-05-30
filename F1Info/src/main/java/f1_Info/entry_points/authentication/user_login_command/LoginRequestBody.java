package f1_Info.entry_points.authentication.user_login_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LoginRequestBody {
    String mEmail;
    String mPassword;

    @JsonCreator
    public LoginRequestBody(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
    ) {
        mEmail = email;
        mPassword = password;
    }
}
