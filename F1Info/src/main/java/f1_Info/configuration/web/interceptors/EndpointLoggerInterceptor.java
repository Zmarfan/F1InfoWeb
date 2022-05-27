package f1_Info.configuration.web.interceptors;

import f1_Info.configuration.web.filters.MyRequestWrapper;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class EndpointLoggerInterceptor implements HandlerInterceptor {
    private static final String HIDDEN = "*****";
    private static final String PASSWORD_PARAMETER = "password";

    private final Logger mLogger;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        mLogger.info(request.getRequestURL().toString(), this.getClass(), String.format(
            "Endpoint called: %s%s, Type: %s, By user: %s, Request ip: %s, Request Body: %s, Response status: %s, Exception: %s",
            request.getRequestURI(),
            getParameters(request),
            request.getMethod(),
            InterceptorUtil.getUser(request),
            InterceptorUtil.getIp(request),
            getRequestBody((MyRequestWrapper) request),
            response.getStatus(),
            exception != null ? exception.getMessage() : "-"
        ));
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
