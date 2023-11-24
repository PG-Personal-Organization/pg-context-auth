package pg.context.auth.domain.context;

/**
 * The type User context not found exception.
 */
public class UserContextNotFoundException extends RuntimeException {
    /**
     * Instantiates a new User context not found exception.
     *
     * @param contextToken the context token
     */
    public UserContextNotFoundException(final String contextToken) {
        super(String.format("UserContext with token: %s not found", contextToken));
    }
}
