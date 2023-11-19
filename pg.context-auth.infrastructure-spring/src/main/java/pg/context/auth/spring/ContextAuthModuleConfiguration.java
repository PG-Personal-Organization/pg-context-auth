package pg.context.auth.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;

/**
 * The type Context auth module configuration.
 */
@Configuration
@EntityScan("pg.context.auth")
@EnableJpaRepositories("pg.context.auth")
@ComponentScan("pg.context.auth")
@Import({
        CommandQueryAutoConfiguration.class,
})
public class ContextAuthModuleConfiguration {
}
