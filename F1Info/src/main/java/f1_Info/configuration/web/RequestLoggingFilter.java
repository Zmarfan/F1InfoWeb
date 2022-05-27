package f1_Info.configuration.web;

import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Order(1)
@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class RequestLoggingFilter implements Filter {
    private static final String HIDDEN = "*****";
    private static final String PASSWORD_PARAMETER = "password";

    private final Logger mLogger;

    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final MyRequestWrapper requestWrapper = new MyRequestWrapper((HttpServletRequest) request);

        mLogger.info(requestWrapper.getRequestURL().toString(), this.getClass(), String.format(
            "%nEndpoint called: %s%s%nType: %s%nBy user: %s%nRequest ip: %s%nRequest body: %s%n",
            requestWrapper.getRequestURI(),
            getParameters(requestWrapper),
            requestWrapper.getMethod(),
            InterceptorUtil.getUser(requestWrapper),
            InterceptorUtil.getIp(requestWrapper),
            getRequestBody(requestWrapper)
        ));

        chain.doFilter(requestWrapper, response);
    }

    private String getParameters(final HttpServletRequest request) {
        final StringBuilder parameters = new StringBuilder();
        final Enumeration<?> parameterEnumeration = request.getParameterNames();
        if (parameterEnumeration.hasMoreElements()) {
            parameters.append("?");
        } else {
            return "";
        }
        while (parameterEnumeration.hasMoreElements()) {
            if (parameters.length() > 1) {
                parameters.append("&");
            }
            final String nextParameter = (String) parameterEnumeration.nextElement();
            parameters.append(nextParameter).append("=");
            if (nextParameter.contains(PASSWORD_PARAMETER)) {
                parameters.append(HIDDEN);
            } else {
                parameters.append(request.getParameter(nextParameter));
            }
        }

        return parameters.toString();
    }

    private String getRequestBody(final MyRequestWrapper requestWrapper) {
        if (requestWrapper.getBody().isEmpty()) {
            return "No Request Body";
        }

        if (requestWrapper.getBody().contains(PASSWORD_PARAMETER)) {
            return HIDDEN;
        }
        return requestWrapper.getBody();
    }
}
