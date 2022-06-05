package f1_Info.entry_points.authentication.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserDetailsRequestBody {
    String mEmail;
    String mPassword;

    @JsonCreator
    public UserDetailsRequestBody(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
    ) {
        mEmail = email;
        mPassword = password;
    }
}
