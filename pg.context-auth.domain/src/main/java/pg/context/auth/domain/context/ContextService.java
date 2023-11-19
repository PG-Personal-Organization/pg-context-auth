package pg.context.auth.domain.context;

import pg.context.auth.domain.user.User;

import java.util.Optional;

/**
 * The interface Context service.
 */
public interface ContextService {
    /**
     * Find by token optional.
     *
     * @param contextToken the context token
     * @return the optional
     */
    Optional<UserContext> findByToken(String contextToken);

    /**
     * Create context for user string.
     *
     * @param user the user
     * @return the string
     */
    String createContextForUser(User user);

    /**
     * Remove context.
     *
     * @param contextToken the context token
     */
    void removeContext(String contextToken);

    /**
     * Clear contexts of user.
     *
     * @param user the user
     */
    void clearContextsOfUser(User user);

    /**
     * Clear old contexts.
     */
    void clearOldContexts();
}
