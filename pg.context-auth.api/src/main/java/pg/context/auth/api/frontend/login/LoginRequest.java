package pg.context.auth.api.frontend.login;

import lombok.*;

import java.io.Serializable;

/**
 * The type Login request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LoginRequest implements Serializable {
    private String username;
    private String password;
}