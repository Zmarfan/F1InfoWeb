package f1_Info.configuration.web;

import f1_Info.configuration.web.users.Authority;
import f1_Info.configuration.web.users.UserManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserManager mUserManager;
    private final CorsConfigurationSource mCorsConfigurationSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mUserManager)
            .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(mCorsConfigurationSource)
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/Authentication/**", "/Reports/**", "/OpenDevelopment/**").permitAll()
            .antMatchers("/User/**", "/Development/**").authenticated()
            .antMatchers("/ManagerDevelopment/**").hasAuthority(Authority.ADMIN.getAuthority())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
