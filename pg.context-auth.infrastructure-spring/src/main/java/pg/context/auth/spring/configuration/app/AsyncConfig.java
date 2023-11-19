package pg.context.auth.spring.configuration.app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pg.context.auth.domain.context.ContextService;

import java.util.concurrent.TimeUnit;

/**
 * The type Async config.
 */
@Configuration
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class AsyncConfig {
    private final ContextService contextService;

    /**
     * Clear context database.
     */
    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 1)
    public void clearContextDatabase() {
        contextService.clearOldContexts();
    }
}
