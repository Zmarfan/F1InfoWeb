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
        // final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // httpServletResponse.setHeader("Access-Control-Allow-Origin", mConfiguration.getRules().getClientDomain());
        // httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        // httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        // httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        // httpServletResponse.setHeader("Access-Control-Max-Age", "180");

        chain.doFilter(new MyRequestWrapper((HttpServletRequest) request), response);
    }
}
