package pg.context.auth.application.cqrs.query.handlers;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import pg.context.auth.domain.context.UserContext;
import pg.lib.cqrs.query.Query;

/**
 * The type User context query.
 */
@ToString
@AllArgsConstructor(staticName = "of")
@Getter
public class UserContextQuery implements Query<UserContext> {
    @NonNull
    @NotEmpty
    private final String contextToken;
}