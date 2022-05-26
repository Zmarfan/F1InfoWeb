package f1_Info.configuration.web;

import f1_Info.entry_points.authentication.SessionAttributes;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class LoggerInterceptor implements HandlerInterceptor {
    private final Logger mLogger;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (!request.getRequestURI().endsWith("error")) {
            mLogger.info(request.getRequestURL().toString(), this.getClass(), String.format(
                "preHandle: %s%s, Type: %s by user: %s, request ip: %s",
                request.getRequestURI(),
                getParameters(request),
                request.getMethod(),
                getUser(request),
                InterceptorUtil.getIp(request)
            ));
        }

        return true;
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
            if (nextParameter.contains("password")) {
                parameters.append("*****");
            } else {
                parameters.append(request.getParameter(nextParameter));
            }
        }

        return parameters.toString();
    }

    private String getUser(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return "Unauthenticated user";
        }
        return session.getAttribute(SessionAttributes.USER_ID).toString();
    }
}
