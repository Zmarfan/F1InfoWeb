package f1_Info.entry_points.authentication;

import common.email.EmailService;
import f1_Info.communication.ClientUriFactory;
import f1_Info.configuration.web.users.UserManager;
import f1_Info.entry_points.authentication.commands.UserDetailsRequestBody;
import f1_Info.entry_points.authentication.commands.enable_user_command.EnableUserCommand;
import f1_Info.entry_points.authentication.commands.forgot_password_command.ForgotPasswordCommand;
import f1_Info.entry_points.authentication.commands.logout_user_command.LogoutUserCommand;
import f1_Info.entry_points.authentication.commands.set_new_user_password_command.SetNewUserPasswordCommand;
import f1_Info.entry_points.authentication.commands.user_login_command.UserLoginCommand;
import f1_Info.entry_points.authentication.commands.user_register_command.UserRegisterCommand;
import f1_Info.entry_points.authentication.services.AuthenticationService;
import f1_Info.entry_points.authentication.services.token_service.TokenService;
import f1_Info.entry_points.helper.BadRequestException;
import f1_Info.entry_points.helper.EndpointHelper;
import f1_Info.entry_points.helper.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.ok;

@RestController
@RequestMapping("/Authentication")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final EndpointHelper mEndpointHelper;
    private final HttpServletRequest mHttpServletRequest;
    private final PasswordEncoder mPasswordEncoder;
    private final AuthenticationService mAuthenticationService;
    private final TokenService mTokenService;
    private final UserManager mUserManager;
    private final EmailService mEmailService;
    private final ClientUriFactory mClientUriFactory;

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isUserLoggedIn() {
        return ok(mEndpointHelper.isLoggedIn());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final UserDetailsRequestBody registerBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to register as this user is already logged in");
            }

            validatePassword(registerBody.getPassword());

            return new UserRegisterCommand(
                mEndpointHelper.convertEmail(registerBody.getEmail()),
                registerBody.getPassword(),
                mPasswordEncoder,
                mUserManager,
                mTokenService,
                mEmailService,
                mClientUriFactory
            );
        });
    }

    @PostMapping("/enable/{token}")
    public ResponseEntity<?> enable(@PathVariable("token") final String token) {
        return mEndpointHelper.runCommand(mHttpServletRequest,userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to enable account as this user is already logged in");
            }

            return new EnableUserCommand(readToken(token), mHttpServletRequest, mTokenService, mUserManager, mAuthenticationService);
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> new LogoutUserCommand(userId, mHttpServletRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody final UserDetailsRequestBody emailBody) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to reset password as this user is already logged in");
            }

            return new ForgotPasswordCommand(mEndpointHelper.convertEmail(emailBody.getEmail()), mTokenService, mUserManager, mEmailService, mClientUriFactory);
        });
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(
        @PathVariable("token") final String token,
        @RequestBody final UserDetailsRequestBody passwordBody
    ) {
        return mEndpointHelper.runCommand(mHttpServletRequest, userId -> {
            if (mEndpointHelper.isLoggedIn()) {
                throw new ForbiddenException("Unable to reset password as this user is already logged in");
            }

            validatePassword(passwordBody.getPassword());

            return new SetNewUserPasswordCommand(
                passwordBody.getPassword(),
                readToken(token),
                mHttpServletRequest,
                mPasswordEncoder,
                mTokenService,
                mUserManager,
                mAuthenticationService
            );
        });
    }

    private void validatePassword(final String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(String.format("The provided password is not at least %s characters long", MIN_PASSWORD_LENGTH));
        }
    }

    private UUID readToken(final String tokenString) throws BadRequestException {
        try {
            return UUID.fromString(tokenString);
        } catch (final IllegalArgumentException e) {
            throw new BadRequestException();
        }
    }
}