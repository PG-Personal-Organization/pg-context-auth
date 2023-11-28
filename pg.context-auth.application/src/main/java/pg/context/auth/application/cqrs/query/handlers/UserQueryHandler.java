package pg.context.auth.application.cqrs.query.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import pg.context.auth.api.cqrs.query.UserQuery;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserNotFoundException;
import pg.context.auth.domain.user.UserService;
import pg.lib.cqrs.query.QueryHandler;

import java.util.Optional;

/**
 * The type User query handler.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserQueryHandler implements QueryHandler<UserQuery, User> {
    private final UserService userService;
    private final Cache usersCache;

    @Override
    public User handle(final UserQuery query) {
        var userId = query.getUserId();
        if (usersCache != null) {
            var user = usersCache.get(userId, User.class);

            if (user != null) {
                log.info("Returning user: {} from cache", user);
                return user;
            }

            user = getUser(userId);
            usersCache.put(userId, user);
            log.info("Context: {} has been fetched from database and put in cache", user);
            return user;
        }

        log.warn("Users cache not available");
        return getUser(userId);
    }

    private User getUser(final String userId) {
        return Optional.ofNullable(userService.findById(userId))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
