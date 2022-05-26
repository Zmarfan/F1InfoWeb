package f1_Info.entry_points.authentication;

import f1_Info.entry_points.authentication.user_login_command.LoginRequestBody;
import f1_Info.entry_points.authentication.user_login_command.UserLoginCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Authentication")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private final HttpServletRequest mHttpServletRequest;
    private final AuthenticationManager mAuthenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginRequestBody loginBody) {
        return new UserLoginCommand(loginBody, mHttpServletRequest, mAuthenticationManager).execute();
    }
}