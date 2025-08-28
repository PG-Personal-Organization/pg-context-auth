package pg.context.auth.standalone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pg.context.auth.spring.ContextAuthModuleConfiguration;

/**
 * The type Context auth test configuration.
 */
@Configuration
@Import({
        ContextAuthModuleConfiguration.class
})
public class ContextAuthTestConfiguration {
}
