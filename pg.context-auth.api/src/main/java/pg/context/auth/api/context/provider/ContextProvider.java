package pg.context.auth.api.context.provider;

import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;

import java.util.Optional;

/**
 * The interface Context provider.
 */
public interface ContextProvider {
    /**
     * Try to get user context optional.
     *
     * @param contextToken the context token
     * @return the optional
     */
    Optional<UserContext> tryToGetUserContext(String contextToken);

    Optional<UserContext> tryToGetUserContext();

    /**
     * Gets user context.
     *
     * @param contextToken the context token
     * @return the user context
     * @throws UserContextNotFoundException the user context not found exception
     */
    UserContext getUserContext(String contextToken) throws UserContextNotFoundException;
}