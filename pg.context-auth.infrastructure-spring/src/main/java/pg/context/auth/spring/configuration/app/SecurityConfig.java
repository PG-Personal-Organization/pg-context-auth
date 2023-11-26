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
