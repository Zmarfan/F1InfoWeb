package f1_Info.configuration.web.filters;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Order(2)
@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class CsrfTokenFilter implements Filter {
    private static final Set<String> ALLOWED_METHODS = Set.of("GET");
    private static final String NO_TOKEN_ENDPOINT_PATH = "/api/v1/Authentication/";
    private static final String HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (!isAllowedCallWithoutToken((HttpServletRequest) request) && !isTokenValid((HttpServletRequest) request)) {
            ((HttpServletResponse)response).sendError(450, "Please provide a valid CSRF token to call this endpoint!");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedCallWithoutToken(final HttpServletRequest request) {
        return ALLOWED_METHODS.contains(request.getMethod()) || request.getRequestURI().startsWith(NO_TOKEN_ENDPOINT_PATH);
    }

    private boolean isTokenValid(final HttpServletRequest request) {
        final HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        return csrfTokenRepository.loadToken(request).getToken().equals((request).getHeader(HEADER_NAME));
    }
}