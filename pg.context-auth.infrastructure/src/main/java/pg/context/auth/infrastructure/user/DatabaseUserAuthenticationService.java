package pg.context.auth.infrastructure.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.user.UserAuthenticationService;
import pg.context.auth.domain.user.UserLoginRequest;
import pg.context.auth.domain.user.UserService;

import java.util.Objects;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class DatabaseUserAuthenticationService implements UserAuthenticationService {
    private final UserService userService;
    private final ContextService contextService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserContext> tryToAuthenticate(final UserLoginRequest loginRequest) {
        var user = userService.findByUsername(loginRequest.getUsername());

        if (Objects.isNull(user)) {
            return Optional.empty();
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.info("User {} authentication successful", user.getUsername());
            var token = contextService.createContextForUser(user);
            return contextService.findByToken(token);
        }
        log.info("User {} authentication failed", user.getUsername());
        return Optional.empty();
    }
}
