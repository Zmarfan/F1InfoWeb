package f1_Info.entry_points.authentication.user_login_and_register_commands.user_login_command;

import common.constants.email.Email;
import f1_Info.entry_points.authentication.user_login_and_register_commands.AuthenticationService;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UserLoginCommand implements Command {
    private final Email mEmail;
    private final String mPassword;
    private final HttpServletRequest mRequest;
    private final AuthenticationService mAuthenticationService;

    @Override
    public String getAction() {
        return String.format("Login user with email: %s and password: *****", mEmail.read());
    }

    @Override
    public ResponseEntity<?> execute() {
        try {
            mAuthenticationService.login(mEmail, mPassword, mRequest);
        } catch (final DisabledException e) {
            return new ResponseEntity<>("Account is disabled", HttpStatus.NOT_ACCEPTABLE);
        } catch (final AuthenticationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.NOT_ACCEPTABLE);
        }

        return ok();
    }
}
