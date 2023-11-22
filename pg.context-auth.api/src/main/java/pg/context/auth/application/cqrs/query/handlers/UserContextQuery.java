package pg.context.auth.application.cqrs.query.handlers;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import pg.context.auth.domain.context.UserContext;
import pg.lib.cqrs.query.Query;

import java.io.Serializable;

/**
 * The type User context query.
 */
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserContextQuery implements Query<UserContext>, Serializable {
    @NonNull
    @NotEmpty
    private String contextToken;
}