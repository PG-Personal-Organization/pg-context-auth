package pg.context.auth.domain.user;

import java.util.List;

/**
 * The interface User repository.
 */
public interface UserDao {
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
     * Add user.
     *
     * @param user the user
     * @return the id
     */
    String addUser(User user);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<User> findAllUsers();
}