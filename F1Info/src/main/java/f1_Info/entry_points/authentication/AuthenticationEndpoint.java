package f1_Info.entry_points.authentication;

import f1_Info.configuration.web.users.UserManager;
import f1_Info.entry_points.authentication.user_login_and_register_commands.UserDetailsRequestBody;
import f1_Info.entry_points.authentication.user_login_and_register_commands.user_login_command.UserLoginCommand;
import f1_Info.entry_points.authentication.user_login_and_register_commands.user_register_command.UserRegisterCommand;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.helper.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Authentication")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final AuthenticationManager mAuthenticationManager;
    private final PasswordEncoder mPasswordEncoder;
    private final UserManager mUserManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final UserDetailsRequestBody registerBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to register as this user is already logged in");
            }

            validatePassword(registerBody.getPassword());

            return new UserRegisterCommand(mEndpointHelper.convertEmail(registerBody.getEmail()), registerBody.getPassword(), mPasswordEncoder, mUserManager);
        });
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final UserDetailsRequestBody loginBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to login as this user is already logged in");
            }

            validatePassword(loginBody.getPassword());

            return new UserLoginCommand(
                mEndpointHelper.convertEmail(loginBody.getEmail()),
                loginBody.getPassword(),
                mHttpServletRequest,
                mAuthenticationManager
            );
        });
    }

    @GetMapping("/")
    public String test() {
        return "<h1>ANYONE</h1>";
    }

    @GetMapping("/user")
    public String user(@RequestParam("test") final long test) {
        return "<h1>I AM USER OR ADMIN</h1>" + test;
    }

    @GetMapping("/admin")
    public String home() {
        return "<h1>I AM ADMIN</h1>";
    }

    private void validatePassword(final String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(String.format("The provided password is not at least %s characters long", MIN_PASSWORD_LENGTH));
        }
    }
}