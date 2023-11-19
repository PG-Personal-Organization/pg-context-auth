package pg.context.auth.domain.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * The type User context.
 */
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserContext implements Serializable {
    private String userId;
    private String username;
    private Set<String> roles;
}