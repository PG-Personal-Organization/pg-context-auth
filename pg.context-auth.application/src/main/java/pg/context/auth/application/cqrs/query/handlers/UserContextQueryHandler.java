package pg.context.auth.application.cqrs.query.handlers;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.context.UserContextNotFoundException;
import pg.context.auth.infrastructure.cache.CacheNames;
import pg.lib.cqrs.query.QueryHandler;

/**
 * The type User context query handler.
 */
@Log4j2
@Service
public class UserContextQueryHandler implements QueryHandler<UserContextQuery, UserContext> {
    private final ContextService contextService;

    private final Cache contextCache;

    /**
     * Instantiates a new User context query handler.
     *
     * @param contextService the context service
     * @param contextCache   the context cache
     */
    public UserContextQueryHandler(final ContextService contextService,
                                   final @Qualifier(CacheNames.CONTEXT_CACHE) Cache contextCache) {
        this.contextService = contextService;
        this.contextCache = contextCache;
    }

    @Override
    public UserContext handle(final UserContextQuery query) {
        val contextToken = query.getContextToken();
        if (contextCache != null) {
            var userContext = contextCache.get(contextToken, UserContext.class);

            if (userContext != null) {
                log.info("Returning context: {} from cache", userContext);
                return userContext;
            }

            userContext = getContext(contextToken);
            contextCache.put(contextToken, userContext);
            log.info("Context: {} has been fetched from database and put in cache", userContext);
            return userContext;
        }
        log.warn("Context cache not available");
        return getContext(contextToken);
    }

    private UserContext getContext(final String contextToken) {
        return contextService.findByToken(contextToken)
                .orElseThrow(UserContextNotFoundException::new);
    }
}