package pg.context.auth.standalone.runners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.user.User;

import java.util.Set;

/**
 * The type User context runner.
 */
@Service
@Profile("devlocal")
@RequiredArgsConstructor
public class UserContextRunner implements ApplicationRunner {
    private final ContextService contextService;

    @Override
    public void run(final ApplicationArguments args) {
        contextService.createContextForUser(User.builder()
                .username("Bob")
                .enabled(true)
                .password("SMTH")
                .roles(Set.of("ADMIN"))
                .build());
    }
}
