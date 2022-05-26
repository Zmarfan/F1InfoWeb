package f1_Info.entry_points.authentication;

import f1_Info.entry_points.authentication.user_login_command.LoginRequestBody;
import f1_Info.entry_points.authentication.user_login_command.UserLoginCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private final HttpServletRequest mHttpServletRequest;
    private final AuthenticationManager mAuthenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginRequestBody loginBody) {
        return new UserLoginCommand(loginBody, mHttpServletRequest, mAuthenticationManager).execute();
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>I AM USER!</h1>";
    }

    @GetMapping("/admin")
    public String home() {
        return "<h1>I AM ADMIN</h1>";
    }
}