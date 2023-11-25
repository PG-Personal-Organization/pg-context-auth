package pg.context.auth.infrastructure.context;

import lombok.experimental.UtilityClass;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.user.User;

import java.util.HashSet;

/**
 * The type Context mapper.
 */
@UtilityClass
public class ContextMapper {
    /**
     * From user context entity.
     *
     * @param user the user
     * @return the context entity
     */
    public ContextEntity fromUser(final User user) {
        return ContextEntity.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .roles(new HashSet<>(user.getRoles()))
                .build();
    }

    /**
     * From entity user context.
     *
     * @param entity the entity
     * @return the user context
     */
    public UserContext fromEntity(final ContextEntity entity) {
        return UserContext.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .roles(new HashSet<>(entity.getRoles()))
                .contextToken(entity.getContextToken())
                .build();
    }
}
