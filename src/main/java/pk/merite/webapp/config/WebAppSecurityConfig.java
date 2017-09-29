package pk.merite.webapp.config;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.View;

import pk.merite.webapp.service.UserService;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(WebAppProps.class)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private WebAppProps props;

    public WebAppSecurityConfig(WebAppProps props, UserService userService) {
        this.userService = userService;
        this.props = props;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/**/favicon.ico", "/webjars/**", "/web/**", "/").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),
                new AntPathRequestMatcher("/webservices/**"))
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .failureHandler(new SimpleUrlAuthenticationFailureHandler())
            .successForwardUrl("/webservices/login/success")
            .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .rememberMe().userDetailsService(userService).alwaysRemember(true)
            .tokenValiditySeconds(props.intValue("rememberme.token.validity.seconds", -1))
            .key(props.get("rmemberme.key", UUID.randomUUID().toString()))
            .rememberMeCookieName(props.get("rmemberme.cookie.name", "AUTH-TOKEN"));
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setHideUserNotFoundExceptions(false);
        authenticationProvider.setUserDetailsService(userService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    @Bean(name = "authenticationManagerBean")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean(name = "error")
    public View defaultErrorView() {
        return new ErrorView();
    }

    public static class ErrorView implements View {
        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        }

        @Override
        public String getContentType() {
            return "text/html";
        }
    }
}
