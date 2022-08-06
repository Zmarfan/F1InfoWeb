package f1_Info.entry_points.authentication.commands.user_login_command;

import common.constants.email.Email;
import f1_Info.configuration.web.interceptors.InterceptorUtil;
import f1_Info.entry_points.authentication.commands.user_login_command.failed_login_handler.FailedLoginHandler;
import f1_Info.entry_points.authentication.services.AuthenticationService;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.notAcceptable;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UserLoginCommand implements Command {
    private final Email mEmail;
    private final String mPassword;
    private final HttpServletRequest mRequest;
    private final FailedLoginHandler mFailedLoginHandler;
    private final AuthenticationService mAuthenticationService;

    @Override
    public String getAction() {
        return String.format("Login user with email: %s and password: *****", mEmail.read());
    }

    @Override
    public ResponseEntity<?> execute() {
        final String ip = InterceptorUtil.getIp(mRequest);
        try {
            if (!mFailedLoginHandler.canMakeAnotherLoginAttempt(ip)) {
                return notAcceptable(new UserLoginResponse(UserLoginResponseType.TOO_MANY_REQUESTS));
            }
            mAuthenticationService.login(mEmail, mPassword, mRequest);
        } catch (final DisabledException e) {
            mFailedLoginHandler.addFailedLoginAttempt(ip);
            return notAcceptable(new UserLoginResponse(UserLoginResponseType.DISABLED));
        } catch (final AuthenticationException e) {
            mFailedLoginHandler.addFailedLoginAttempt(ip);
            return notAcceptable(new UserLoginResponse(UserLoginResponseType.INVALID_CREDENTIALS));
        }

        return ok();
    }
}
