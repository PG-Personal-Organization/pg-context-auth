package pg.context.auth.domain.user;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Instantiates a new User not found exception.
     *
     * @param userId the user id
     */
    public UserNotFoundException(final String userId) {
        super(String.format("User with id: %s not found", userId));
    }
}
