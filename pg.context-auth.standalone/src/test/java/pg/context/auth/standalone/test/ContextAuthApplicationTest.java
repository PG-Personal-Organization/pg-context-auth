package pg.context.auth.standalone.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;
import pg.context.auth.standalone.config.ContextAuthIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Context auth application test.
 */
@ContextAuthIntegrationTest
@EmbeddedKafka(brokerProperties = { "transaction.max.timeout.ms=3600000" })
class ContextAuthApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Should app startup correctly.
     */
    @Test
    void shouldAppStartupCorrectly() {
        assertNotNull(applicationContext);
    }
}