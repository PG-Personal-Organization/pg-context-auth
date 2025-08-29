package pg.context.auth.spring.configuration.app;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.context.auth.application.cqrs.query.handlers.UserContextQueryHandler;
import pg.context.auth.application.cqrs.query.handlers.UserQueryHandler;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.user.UserService;

import java.util.Objects;

@Configuration
public class ContextAuthCqrsConfiguration {

    @Bean
    public UserContextQueryHandler userContextQueryHandler(final ContextService contextService, final CacheManager cacheManager) {
        var contextCache = Objects.requireNonNull(cacheManager.getCache("contextCache"));
        return new UserContextQueryHandler(contextService, contextCache);
    }

    @Bean
    public UserQueryHandler userQueryHandler(final UserService userService, final CacheManager cacheManager) {
        var usersCache = Objects.requireNonNull(cacheManager.getCache("usersCache"));
        return new UserQueryHandler(userService, usersCache);
    }
}
