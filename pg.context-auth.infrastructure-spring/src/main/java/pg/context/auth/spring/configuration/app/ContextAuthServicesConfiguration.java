package pg.context.auth.spring.configuration.app;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.user.UserService;
import pg.context.auth.infrastructure.context.ContextRepository;
import pg.context.auth.infrastructure.context.ContextSecurityService;
import pg.context.auth.infrastructure.context.DatabaseContextService;
import pg.context.auth.infrastructure.user.DatabaseUserService;
import pg.context.auth.infrastructure.user.UserRepository;

import java.util.Objects;

@Configuration
public class ContextAuthServicesConfiguration {

    @Bean
    public ContextService contextService(final ContextRepository contextRepository, final ContextSecurityService contextSecurityService, final CacheManager cacheManager) {
        var contextCache = Objects.requireNonNull(cacheManager.getCache("contextCache"));
        return new DatabaseContextService(contextRepository, contextSecurityService, contextCache);
    }

    @Bean
    public UserService userService(final UserRepository userRepository, final PasswordEncoder passwordEncoder, final CacheManager cacheManager) {
        var usersCache = Objects.requireNonNull(cacheManager.getCache("usersCache"));
        return new DatabaseUserService(userRepository, passwordEncoder, usersCache);
    }
}
