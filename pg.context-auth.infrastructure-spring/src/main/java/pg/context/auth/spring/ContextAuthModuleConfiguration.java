package pg.context.auth.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pg.lib.common.spring.config.CommonModuleConfiguration;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;
import pg.lib.remote.cqrs.config.RemoteModulesCqrsConfiguration;

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
        RemoteModulesCqrsConfiguration.class
})
public class ContextAuthModuleConfiguration {
}
