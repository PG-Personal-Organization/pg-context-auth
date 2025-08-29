package pg.context.auth.api.security;

import org.springframework.context.annotation.*;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.context.provider.remote.RemoteUserContextConfiguration;
import pg.lib.common.spring.auth.HeaderAuthenticationFilter;
import pg.lib.common.spring.storage.HeadersHolder;

/**
 * The type Context based security configuration.
 */
@Configuration
@Import({
        RemoteUserContextConfiguration.class
})
public class ContextBasedSecurityConfiguration {

    @Bean
    public HeaderAuthenticationFilter userSecurityFilter(final @Lazy ContextProvider contextProvider, final @Lazy HeadersHolder headersHolder) {
        return new UserSecurityFilter(contextProvider, headersHolder);
    }
}
