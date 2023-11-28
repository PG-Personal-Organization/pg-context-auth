package pg.context.auth.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.context.provider.local.LocalUserContextProvider;
import pg.context.auth.api.frontend.HttpEndpointPaths;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.user.Roles;
import pg.lib.cqrs.service.ServiceExecutor;

/**
 * The type Security config.
 */
@Configuration
public class SecurityConfig {

    /**
     * Login request customizer customizer.
     *
     * @return the customizer
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> loginRequestCustomizer() {
        return requests -> requests.requestMatchers(HttpMethod.POST, HttpEndpointPaths.LOGIN)
                .anonymous()
                ;
    }

    /**
     * Users request customizer customizer.
     *
     * @return the customizer
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> usersRequestCustomizer() {
        return requests -> requests.requestMatchers(HttpEndpointPaths.GET_USERS)
                .hasRole(Roles.ADMIN.name())
                ;
    }

    /**
     * Users request customizer customizer.
     *
     * @return the customizer
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> cqrsRequestCustomizer() {
        return requests -> requests
                .requestMatchers(HttpEndpointPaths.USER_CONTEXT_QUERY).permitAll()
                .requestMatchers(HttpEndpointPaths.USER_QUERY).hasRole(Roles.USER.name())
                ;
    }

    /**
     * Context provider context provider.
     *
     * @param serviceExecutor the service executor
     * @return the context provider
     */
    @Bean
    public ContextProvider<UserContext> contextProvider(final ServiceExecutor serviceExecutor) {
        return new LocalUserContextProvider(serviceExecutor);
    }
}
