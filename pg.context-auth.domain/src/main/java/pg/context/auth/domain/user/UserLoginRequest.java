package pg.context.auth.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User login request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    private String username;
    private String password;
}
