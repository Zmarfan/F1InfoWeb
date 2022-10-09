package f1_Info.configuration.web.filters;

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
    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // Since the MyRequestWrapper closes the input stream of the body before Spring has a chance to read for file sending we can't wrap it.
        if (requestIsFileUpload((HttpServletRequest) request)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new MyRequestWrapper((HttpServletRequest) request), response);
        }
    }

    private boolean requestIsFileUpload(final HttpServletRequest request) {
        final String contentType = request.getHeader("content-type");
        return contentType != null && contentType.contains("multipart/form-data");
    }
}
