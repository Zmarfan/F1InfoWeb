package f1_Info.entry_points.authentication.user_login_and_register_commands.user_register_command;

import common.constants.email.Email;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.UserManager;
import f1_Info.configuration.web.users.exceptions.UnableToRegisterUserException;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static f1_Info.configuration.web.ResponseUtil.conflict;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UserRegisterCommand implements Command {
    private final Email mEmail;
    private final String mPassword;
    private final PasswordEncoder mPasswordEncoder;
    private final UserManager mUserManager;

    @Override
    public String getAction() {
        return String.format("Register email: %s with password: *****", mEmail);
    }

    @Override
    public ResponseEntity<?> execute() {
        try {
            final long userId = mUserManager.registerUser(F1UserDetails.createNewUser(mEmail, mPassword));
            return ok();
        } catch (final UnableToRegisterUserException e) {
            return conflict("Unable to register this user, try again later!");
        }
    }
}
