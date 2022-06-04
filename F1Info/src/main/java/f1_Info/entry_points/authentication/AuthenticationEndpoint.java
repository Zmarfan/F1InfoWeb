package f1_Info.entry_points.authentication;

import f1_Info.configuration.web.users.UserManager;
import f1_Info.entry_points.authentication.user_login_and_register_commands.AuthenticationService;
import f1_Info.entry_points.authentication.user_login_and_register_commands.UserDetailsRequestBody;
import f1_Info.entry_points.authentication.user_login_and_register_commands.enable_user_command.EnableUserCommand;
import f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service.RegisterTokenService;
import f1_Info.entry_points.authentication.user_login_and_register_commands.user_login_command.UserLoginCommand;
import f1_Info.entry_points.authentication.user_login_and_register_commands.user_register_command.UserRegisterCommand;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.helper.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/Authentication")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final AuthenticationService mAuthenticationService;
    private final RegisterTokenService mRegisterTokenService;
    private final UserManager mUserManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final UserDetailsRequestBody registerBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to register as this user is already logged in");
            }

            validatePassword(registerBody.getPassword());

            return new UserRegisterCommand(mEndpointHelper.convertEmail(registerBody.getEmail()), registerBody.getPassword(), mUserManager);
        });
    }

    @PostMapping("/enable/{token}")
    public ResponseEntity<?> enable(@PathVariable("token") final String tokenString) {
        return mEndpointHelper.runCommand(mHttpServletRequest,userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to enable account as this user is already logged in");
            }

            final UUID token;
            try {
                token = UUID.fromString(tokenString);
            } catch (final IllegalArgumentException e) {
                throw new BadRequestException();
            }

            return new EnableUserCommand(token, mHttpServletRequest, mRegisterTokenService, mUserManager, mAuthenticationService);
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
                mAuthenticationService
            );
        });
    }

    private void validatePassword(final String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(String.format("The provided password is not at least %s characters long", MIN_PASSWORD_LENGTH));
        }
    }
}