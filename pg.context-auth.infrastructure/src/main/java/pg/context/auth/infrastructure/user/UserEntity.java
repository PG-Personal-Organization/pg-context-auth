package pg.context.auth.infrastructure.user;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type User entity.
 */
@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean enabled;

    @ElementCollection(targetClass = String.class)
    private Set<String> roles;

    /**
     * Instantiates a new User entity.
     *
     * @param username the username
     * @param password the password
     * @param enabled  the enabled
     * @param roles    the roles
     */
    public UserEntity(String username, String password, boolean enabled, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

}
