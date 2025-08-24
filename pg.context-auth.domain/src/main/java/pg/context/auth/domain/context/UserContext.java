package pg.context.auth.domain.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * The type User context.
 */
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserContext implements Serializable {
    private String userId;
    private String username;
    private Set<String> roles;

    @JsonIgnore
    private String contextToken;

    public boolean hasPermission(final @NonNull String permission) {
        return roles.contains(permission);
    }

    public boolean hasAllPermissions(final @NonNull Set<String> permissions) {
        return permissions.stream().allMatch(this::hasPermission);
    }
}