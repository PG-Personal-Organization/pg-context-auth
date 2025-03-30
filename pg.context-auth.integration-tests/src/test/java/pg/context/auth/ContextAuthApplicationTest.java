package pg.context.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Context auth application test.
 */
@ContextAuthIntegrationTest
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