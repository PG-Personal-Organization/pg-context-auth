package pg.context.auth.api.context.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;
import pg.lib.common.spring.auth.HeaderNames;
import pg.lib.common.spring.storage.HeadersHolder;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public abstract class CachingContextProvider implements ContextProvider<UserContext> {
    private final Cache contextCache;
    private final HeadersHolder headersHolder;

    @Override
    public Optional<UserContext> tryToGetUserContext(final String contextToken) {
        var cachedContext = tryToGetUserContextFromCache(contextToken);
        if (cachedContext.isPresent()) {
            log.debug("Context {} found in cache", cachedContext);
            return cachedContext;
        }

        UserContext result = null;
        var query = toUserContextQuery(contextToken);

        try {
            result = executeQuery(query);
            log.info("Fetching context {} ended successfully", result);
        } catch (final Exception e) {
            log.error("Error appeared when trying to fetch context", e);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserContext> tryToGetUserContext() {
        var contextToken = headersHolder.tryToGetHeader(HeaderNames.CONTEXT_TOKEN);
        if (contextToken.isEmpty()) {
            return Optional.empty();
        }
        return contextToken.map(this::getUserContext);
    }

    @Override
    public UserContext getUserContext(final String contextToken) throws UserContextNotFoundException {
        var cachedContext = tryToGetUserContextFromCache(contextToken);
        if (cachedContext.isPresent()) {
            log.debug("Context {} found in cache", cachedContext);
            return cachedContext.get();
        }

        UserContext result;
        var query = toUserContextQuery(contextToken);

        try {
            result = executeQuery(query);
        } catch (final Exception e) {
            if (e instanceof UserContextNotFoundException ex) {
                throw ex;
            }
            throw new UserContextNotFoundException(contextToken);
        }

        log.info("Fetching context {} ended successfully", result);
        return result;
    }

    private Optional<UserContext> tryToGetUserContextFromCache(final String contextToken) {
        return Optional.ofNullable(contextCache).map(cache -> cache.get(contextToken, UserContext.class));
    }

    private UserContextQuery toUserContextQuery(final String contextToken) {
        return UserContextQuery.of(contextToken);
    }

    protected abstract UserContext executeQuery(UserContextQuery query);
}
