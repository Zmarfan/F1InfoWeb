package f1_Info.entry_points.authentication.commands.set_new_user_password_command;

import f1_Info.configuration.web.users.UserManager;
import f1_Info.entry_points.authentication.services.AuthenticationService;
import f1_Info.entry_points.authentication.services.token_service.TokenService;
import f1_Info.entry_points.authentication.services.token_service.TokenStatusType;
import f1_Info.entry_points.authentication.services.token_service.TokenUserErrorResponse;
import f1_Info.entry_points.authentication.services.token_service.UserInformation;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.forbidden;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class SetNewUserPasswordCommand implements Command {
    private final String mNewPassword;
    private final UUID mToken;
    private final HttpServletRequest mRequest;
    private final PasswordEncoder mPasswordEncoder;
    private final TokenService mTokenService;
    private final UserManager mUserManager;
    private final AuthenticationService mAuthenticationService;

    @Override
    public String getAction() {
        return "Set new password for user";
    }

    @Override
    public ResponseEntity<?> execute() {
        final Optional<UserInformation> user = mTokenService.findUserFromToken(mToken);
        if (user.isEmpty()) {
            return forbidden();
        }
        if (user.get().getStatusType() == TokenStatusType.TIMED_OUT) {
            return forbidden(new TokenUserErrorResponse(TokenStatusType.TIMED_OUT));
        }

        final String hashedPassword = mPasswordEncoder.encode(mNewPassword);
        if (mPasswordEncoder.matches(mNewPassword, user.get().getPassword())) {
            return forbidden(new TokenUserErrorResponse(TokenStatusType.SAME_PASSWORD));
        }

        mUserManager.setNewPasswordForUser(user.get().getUserId(), hashedPassword);
        mAuthenticationService.login(user.get().getEmail(), mNewPassword, mRequest);

        return ok();
    }
}
