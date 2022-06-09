package f1_Info.entry_points.authentication.commands.enable_user_command;

import f1_Info.configuration.web.users.UserManager;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import f1_Info.entry_points.authentication.services.AuthenticationService;
import f1_Info.entry_points.authentication.services.register_token_service.RegisterTokenService;
import f1_Info.entry_points.authentication.services.register_token_service.RegisterTokenStatusType;
import f1_Info.entry_points.authentication.services.register_token_service.UserInformation;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.*;

@AllArgsConstructor
public class EnableUserCommand implements Command {
    private final UUID mToken;
    private final HttpServletRequest mRequest;
    private final RegisterTokenService mRegisterTokenService;
    private final UserManager mUserManager;
    private final AuthenticationService mAuthenticationService;

    @Override
    public String getAction() {
        return "enable user with token";
    }

    @Override
    public ResponseEntity<?> execute() {
        final Optional<UserInformation> user = mRegisterTokenService.findUserFromToken(mToken);
        if (user.isEmpty()) {
            return forbidden();
        }
        if (user.get().getStatusType() != RegisterTokenStatusType.VALID) {
            return forbidden(new EnableUserErrorResponse(user.get().getStatusType()));
        }

        try {
            mUserManager.enableUser(user.get().getUserId());
        } catch (final UnableToRegisterUserException e) {
            return forbidden();
        }

        mAuthenticationService.autoLogin(user.get().getUserId(), user.get().getEmail(), mRequest);

        return ok();
    }
}
