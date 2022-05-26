package f1_Info.configuration.web;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class WebMvcConfig implements WebMvcConfigurer {
    private final RateLimitInterceptor mRateLimitInterceptor;
    private final LoggerInterceptor mLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mLoggerInterceptor);
        registry.addInterceptor(mRateLimitInterceptor);
    }
}
