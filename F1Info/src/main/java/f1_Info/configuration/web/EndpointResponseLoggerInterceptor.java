package f1_Info.configuration.web;

import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class EndpointResponseLoggerInterceptor implements HandlerInterceptor {
    private final Logger mLogger;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        mLogger.info(request.getRequestURL().toString(), this.getClass(), String.format(
            "%nEndpoint completed: %s%nType: %s%nBy user: %s%nRequest ip: %s%nResponse status: %s%nException: %s%n",
            request.getRequestURI(),
            request.getMethod(),
            InterceptorUtil.getUser(request),
            InterceptorUtil.getIp(request),
            response.getStatus(),
            exception != null ? exception.getMessage() : "-"
        ));
    }
}
