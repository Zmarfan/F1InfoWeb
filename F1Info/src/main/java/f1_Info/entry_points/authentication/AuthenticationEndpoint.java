package f1_Info.entry_points.authentication;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class AuthenticationEndpoint {
    private final AuthenticationManager mAuthenticationManager;

    @GetMapping("/")
    public ResponseEntity<TestResponse> all() {
        return new ResponseEntity<>(new TestResponse("Detta funkar"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
        final HttpServletRequest request,
        @RequestBody final LoginRequestBody loginBody
    ) {
        try {
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            final Authentication authentication = mAuthenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession(true);
        } catch (final AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>I AM USER OR ADMIN</h1>";
    }

    @GetMapping("/admin")
    public String home() {
        return "<h1>I AM ADMIN</h1>";
    }
}