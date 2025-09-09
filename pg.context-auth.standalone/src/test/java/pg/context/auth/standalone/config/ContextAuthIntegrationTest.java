package pg.context.auth.standalone.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pg.context.auth.standalone.ContextAuthApplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Context auth integration test.
 */
@SpringBootTest(
        classes = ContextAuthApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.kafka.consumer.auto-offset-reset=earliest",
                "spring.kafka.consumer.group-id=test-group",
                "pg.kafka.bootstrap-server=${spring.embedded.kafka.brokers}",
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
        }
)
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@Import({
        ContextAuthTestConfiguration.class
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextAuthIntegrationTest {
}