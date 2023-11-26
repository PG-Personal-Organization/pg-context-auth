package pg.context.auth.standalone.runners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserService;

import java.util.Set;

/**
 * The type Users runner.
 */
@Service
@Profile("devlocal")
@RequiredArgsConstructor
public class UsersRunner implements ApplicationRunner {
    private final UserService userService;

    @Override
    public void run(final ApplicationArguments args) {
        userService.addUser(User.builder()
                .username("Bob")
                .enabled(true)
                .password("SMTH")
                .roles(Set.of("ADMIN"))
                .build());
    }
}
