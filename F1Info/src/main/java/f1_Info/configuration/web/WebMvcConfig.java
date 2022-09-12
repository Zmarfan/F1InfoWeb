package f1_Info.configuration.web;

import f1_Info.configuration.web.interceptors.EndpointLoggerInterceptor;
import f1_Info.configuration.web.interceptors.RateLimitInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(List.of(mConfiguration.getRules().getClientDomain()));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "X-XSRF-TOKEN"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(180L);

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
