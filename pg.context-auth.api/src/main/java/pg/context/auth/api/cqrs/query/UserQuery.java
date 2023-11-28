package pg.context.auth.api.cqrs.query;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pg.context.auth.domain.user.User;
import pg.lib.cqrs.query.Query;

/**
 * The type User query.
 */
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserQuery implements Query<User> {
    @NonNull
    @NotBlank
    private String userId;
}
