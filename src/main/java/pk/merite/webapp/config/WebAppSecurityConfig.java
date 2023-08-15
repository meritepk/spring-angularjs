package pk.merite.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebAppSecurityConfig {

    @Value("${spring.web.security.ignored:/,/error,/web/**,/webjars/**,/favicon.ico}")
    private String[] ignored = { "/", "/error", "/web/**", "/webjars/**", "/favicon.ico" };

    @Value("${rememberme.token.validity.seconds:-1}")
    private int tokenValiditySeconds;

    @Value("${rememberme.token.key:${random.uuid}}")
    private String tokenKey;

    @Value("${rememberme.token.cookie:SESSIONKEY}")
    private String tokenCookieName;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(ignored);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return configure(http).build();
    }

    private HttpSecurity configure(HttpSecurity http) throws Exception {
        http.formLogin(customizer -> customizer.loginPage("/login").permitAll()
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .successForwardUrl("/api/v1/login/success"));
        http.exceptionHandling(customizer -> customizer.defaultAuthenticationEntryPointFor(
                new Http403ForbiddenEntryPoint(), new AntPathRequestMatcher("/api/**")));
        http.csrf(customizer -> customizer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()) // XorCsrfTokenRequestAttributeHandler()::handle)
                .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy()));
        http.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());
        http.rememberMe(customizer -> customizer.alwaysRemember(true)
                .tokenValiditySeconds(tokenValiditySeconds)
                .key(tokenKey)
                .rememberMeCookieName(tokenCookieName));
        http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http;
    }
}
