package f1_Info.entry_points.authentication.user_login_command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LoginRequestBody {
    String mUsername;
    String mPassword;

    @JsonCreator
    public LoginRequestBody(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
    ) {
        mUsername = username;
        mPassword = password;
    }
}
