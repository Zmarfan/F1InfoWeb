package f1_Info.entry_points.authentication.user_login_command;

import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.constants.Authority;
import f1_Info.entry_points.authentication.SessionAttributes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

import static f1_Info.utils.ResponseUtil.forbidden;
import static f1_Info.utils.ResponseUtil.ok;

@AllArgsConstructor
public class UserLoginCommand {
    private final LoginRequestBody mLoginBody;
    private final HttpServletRequest mRequest;
    private final AuthenticationManager mAuthenticationManager;

    public ResponseEntity<?> execute() {
        if (isAlreadyLoggedIn()) {
            return forbidden();
        }

        try {
            final Authentication authentication = createAuthentication();
            initSession(authentication);
        } catch (final AuthenticationException e) {
            return forbidden();
        }

        return ok();
    }

    private boolean isAlreadyLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authentication.getAuthorities().contains(Authority.ROLE_ANONYMOUS) && authentication.isAuthenticated();
    }

    private Authentication createAuthentication() {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mLoginBody.getUsername(), mLoginBody.getPassword());
        token.setDetails(new WebAuthenticationDetails(mRequest));
        return mAuthenticationManager.authenticate(token);
    }

    private void initSession(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final F1UserDetails userDetails = (F1UserDetails) authentication.getPrincipal();
        mRequest.getSession(true).setAttribute(SessionAttributes.USER_ID, userDetails.getUserId());
    }
}
