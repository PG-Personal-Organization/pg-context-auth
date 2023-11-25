package pg.context.auth.domain.user;

import pg.context.auth.domain.context.UserContext;

import java.util.Optional;

public interface UserAuthenticationService {
    Optional<UserContext> tryToAuthenticate(UserLoginRequest loginRequest);
}
