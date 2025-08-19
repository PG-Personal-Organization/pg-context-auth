package pg.context.auth.api.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pg.context.auth.api.context.provider.remote.RemoteUserContextConfiguration;

/**
 * The type Context based security configuration.
 */
@Configuration
@Import({
        RemoteUserContextConfiguration.class
})
@ComponentScan(basePackages = "pg.context.auth.api.security")
public class ContextBasedSecurityConfiguration {

}
