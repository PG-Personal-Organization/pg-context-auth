package pg.context.auth.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pg.context.auth.spring.configuration.app.ContextAuthCqrsConfiguration;
import pg.context.auth.spring.configuration.app.ContextAuthServicesConfiguration;
import pg.kafka.config.KafkaConfiguration;
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
        ContextAuthCqrsConfiguration.class,
        ContextAuthServicesConfiguration.class,

        KafkaConfiguration.class
})
public class ContextAuthModuleConfiguration {
}
