package pg.context.auth.domain.user;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Find by username user.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    User findById(String id);

    /**
     * Add user string.
     *
     * @param user the user
     * @return the string
     */
    String addUser(User user);

    /**
     * Find all users list.
     *
     * @return the list
     */
    List<User> findAllUsers();
}