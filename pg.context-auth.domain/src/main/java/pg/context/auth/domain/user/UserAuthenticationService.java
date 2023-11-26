package pg.context.auth.domain.user;

import pg.context.auth.domain.context.UserContext;

import java.util.Optional;

/**
 * The interface User authentication service.
 */
public interface UserAuthenticationService {
    /**
     * Try to authenticate optional.
     *
     * @param loginRequest the login request
     * @return the optional
     */
    Optional<UserContext> tryToAuthenticate(UserLoginRequest loginRequest);
}
