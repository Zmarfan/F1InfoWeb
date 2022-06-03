package f1_Info.entry_points.authentication.user_login_and_register_commands.enable_user_command;

import f1_Info.configuration.web.users.UserManager;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import f1_Info.entry_points.authentication.user_login_and_register_commands.register_token_service.RegisterTokenService;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static f1_Info.configuration.web.ResponseUtil.forbidden;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class EnableUserCommand implements Command {
    final UUID mToken;
    final RegisterTokenService mRegisterTokenService;
    final UserManager mUserManager;

    @Override
    public String getAction() {
        return String.format("enable user with token: %s", mToken.toString());
    }

    @Override
    public ResponseEntity<?> execute() {
        final Optional<Long> userId = mRegisterTokenService.findDisabledUserFromToken(mToken);
        if (userId.isEmpty()) {
            return forbidden();
        }
        try {
            mUserManager.enableUser(userId.get());
        } catch (final UnableToRegisterUserException e) {
            return forbidden();
        }
        return ok();
    }
}
