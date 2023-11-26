package pg.context.auth.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pg.lib.common.spring.config.CommonModuleConfiguration;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;

/**
 * The type Context auth module configuration.
 */
@Configuration
@EntityScan("pg.context.auth")
@EnableJpaRepositories("pg.context.auth")
@ComponentScan("pg.context.auth")
@Import({
        CommonModuleConfiguration.class,
        CommandQueryAutoConfiguration.class,
})
public class ContextAuthModuleConfiguration {
}
