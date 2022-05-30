package f1_Info.entry_points.authentication;

import common.constants.email.Email;
import common.constants.email.MalformedEmailException;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.authentication.user_login_command.LoginRequestBody;
import f1_Info.entry_points.authentication.user_login_command.UserLoginCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginRequestBody loginBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (loginBody.getPassword() == null || loginBody.getPassword().length() < MIN_PASSWORD_LENGTH) {
                throw new BadRequestException(String.format("The provided password is not at least %s characters long", MIN_PASSWORD_LENGTH));
            }

            final Email email;
            try {
                email = new Email(loginBody.getEmail());
            } catch (final MalformedEmailException e) {
                throw new BadRequestException("The provided email is not a valid email address");
            }

            return new UserLoginCommand(email, loginBody.getPassword(), mHttpServletRequest, mAuthenticationManager);
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
}