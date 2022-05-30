package f1_Info.entry_points.authentication.user_login_command;

import common.constants.email.Email;
import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.SessionAttributes;
import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.configuration.web.ResponseUtil.forbidden;
import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class UserLoginCommand implements Command {
    private final Email mEmail;
    private final String mPassword;
    private final HttpServletRequest mRequest;
    private final AuthenticationManager mAuthenticationManager;

    @Override
    public String getAction() {
        return String.format("Login user with email: %s and password: *****", mEmail.read());
    }

    @Override
    public ResponseEntity<?> execute() {
        if (isAlreadyLoggedIn()) {
            return forbidden("Unable to login as this user is already logged in");
        }

        try {
            final Authentication authentication = createAuthentication();
            initSession(authentication);
        } catch (final AuthenticationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.NOT_ACCEPTABLE);
        }

        return ok();
    }

    private boolean isAlreadyLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authentication.getAuthorities().contains(Authority.ROLE_ANONYMOUS) && authentication.isAuthenticated();
    }

    private Authentication createAuthentication() {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mEmail.read(), mPassword);
        token.setDetails(new WebAuthenticationDetails(mRequest));
        return mAuthenticationManager.authenticate(token);
    }

    private void initSession(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final F1UserDetails userDetails = (F1UserDetails) authentication.getPrincipal();
        mRequest.getSession(true).setAttribute(SessionAttributes.USER_ID, userDetails.getUserId());
    }
}
