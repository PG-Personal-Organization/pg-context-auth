package pg.context.auth.domain.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.function.UnaryOperator;

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
    private static final UnaryOperator<String> ROLES_BUILDER = permission -> "ROLE_" + permission.toUpperCase();

    private String userId;
    private String username;
    private Set<String> roles;

    @JsonIgnore
    private String contextToken;

    public boolean hasPermission(final @NonNull String permission) {
        return roles.contains(ROLES_BUILDER.apply(permission));
    }

    public boolean hasAllPermissions(final @NonNull Set<String> permissions) {
        if (permissions.isEmpty()) {
            return true;
        }
        return permissions.stream().allMatch(this::hasPermission);
    }
}