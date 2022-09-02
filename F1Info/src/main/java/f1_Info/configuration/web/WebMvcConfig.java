package f1_Info.configuration.web;

import f1_Info.configuration.web.interceptors.EndpointLoggerInterceptor;
import f1_Info.configuration.web.interceptors.RateLimitInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class WebMvcConfig implements WebMvcConfigurer {
    private final RateLimitInterceptor mRateLimitInterceptor;
    private final EndpointLoggerInterceptor mEndpointLoggerInterceptor;
    private final common.configuration.Configuration mConfiguration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mRateLimitInterceptor);
        registry.addInterceptor(mEndpointLoggerInterceptor);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(mConfiguration.getRules().getClientDomain())
            .allowedHeaders("Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
            .allowCredentials(true)
            .maxAge(180);
    }
}
