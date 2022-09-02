package f1_Info.configuration.web.filters;

import common.configuration.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@Component
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class RequestFilter implements Filter {
    private final Configuration mConfiguration;

    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new MyRequestWrapper((HttpServletRequest) request), response);
    }
}
