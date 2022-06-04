package f1_Info.entry_points.authentication.user_login_and_register_commands;

import common.constants.email.Email;
import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.F1UserDetails;
import f1_Info.configuration.web.users.SecurityContextWrapper;
import f1_Info.configuration.web.users.SessionAttributes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationService {
    private final SecurityContextWrapper mSecurityContextWrapper;
    private final AuthenticationManager mAuthenticationManager;

    public void login(final Email email, final String password, final HttpServletRequest request) throws AuthenticationException {
        final Authentication authentication = createAuthentication(email, password, request);
        mSecurityContextWrapper.setAuthentication(authentication);
        final F1UserDetails userDetails = (F1UserDetails) authentication.getPrincipal();
        initSessionWithUserId(userDetails.getUserId().orElseThrow(), request);
    }

    public void autoLogin(final long userId, final Email email, final HttpServletRequest request) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email.read(), null, List.of(Authority.USER));
        mSecurityContextWrapper.setAuthentication(token);
        initSessionWithUserId(userId, request);
    }

    private Authentication createAuthentication(final Email email, final String password, final HttpServletRequest request) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email.read(), password);
        token.setDetails(new WebAuthenticationDetails(request));
        return mAuthenticationManager.authenticate(token);
    }

    private void initSessionWithUserId(final long userId, final HttpServletRequest request) {
        request.getSession(true).setAttribute(SessionAttributes.USER_ID, userId);
    }
}
