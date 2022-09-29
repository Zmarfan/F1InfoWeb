package f1_Info.configuration.web;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException auth) throws IOException {
        setResponseError(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN, String.format("Access Denies: %s", accessDeniedException.getMessage()));
    }

    @ExceptionHandler (value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        setResponseError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("Internal Server Error: %s", exception.getMessage()));
    }

    private void setResponseError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException{
        response.setStatus(errorCode);
        response.getWriter().write(errorMessage);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
