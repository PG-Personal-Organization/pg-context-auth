package pg.context.auth.domain.context;

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
}