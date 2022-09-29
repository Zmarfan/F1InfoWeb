package f1_Info.configuration.web;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@EnableWebSecurity
public class SessionListener implements HttpSessionListener {
    private static final int MAX_SESSION_TIME_IN_SECONDS = 86400; // 1 day

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(MAX_SESSION_TIME_IN_SECONDS);
    }
}