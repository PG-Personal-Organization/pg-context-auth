package pg.context.auth.infrastructure.user;

import lombok.experimental.UtilityClass;
import pg.context.auth.domain.user.User;

/**
 * The type User mapper.
 */
@UtilityClass
public class UserMapper {
    /**
     * From entity user.
     *
     * @param entity the entity
     * @return the user
     */
    User fromEntity(final UserEntity entity) {
        return User.builder()
                .id(String.valueOf(entity.getId()))
                .username(entity.getUsername())
                .password(entity.getPassword())
                .enabled(entity.isEnabled())
                .roles(entity.getRoles())
                .build();
    }

    /**
     * From dto user entity.
     *
     * @param user the user
     * @return the user entity
     */
    UserEntity fromDto(final User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .build();
    }
}