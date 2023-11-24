package pg.context.auth.standalone.runners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.user.User;
import pg.context.auth.infrastructure.context.ContextRepositoryService;

import java.util.Set;

/**
 * The type User context runner.
 */
@Service
@RequiredArgsConstructor
public class UserContextRunner implements ApplicationRunner {
    private final ContextRepositoryService contextService;

    @Override
    public void run(final ApplicationArguments args) {
        contextService.createContextForUser(User.builder()
                        .id("1")
                        .username("Bob")
                        .enabled(true)
                        .password("SMTH")
                        .roles(Set.of("ADMIN"))
                .build());
    }
}
