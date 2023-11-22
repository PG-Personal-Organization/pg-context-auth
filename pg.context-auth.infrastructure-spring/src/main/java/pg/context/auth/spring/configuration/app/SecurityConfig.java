package pg.context.auth.spring.configuration.app;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * The type Security config.
 */
@Configuration
public class SecurityConfig {

    /**
     * Role hierarchy.
     *
     * @return the role hierarchy
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    /**
     * Method security expression handler method security expression handler.
     *
     * @param roleHierarchy the role hierarchy
     * @return the method security expression handler
     */
    @Bean
    static @NonNull MethodSecurityExpressionHandler methodSecurityExpressionHandler(final @NonNull RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    /**
     * Security filter chain security filter chain.
     *
     * @param http                   the http
     * @param authenticationProvider the authentication provider
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final @NonNull HttpSecurity http,
                                                   final @NonNull AuthenticationProvider authenticationProvider,
                                                   final @NonNull CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/actuator/**", "/swagger-ui/**", "/swagger-ui.html**", "/v3/api-docs/**", "/**").permitAll())
        ;

        return http.build();
    }
}
