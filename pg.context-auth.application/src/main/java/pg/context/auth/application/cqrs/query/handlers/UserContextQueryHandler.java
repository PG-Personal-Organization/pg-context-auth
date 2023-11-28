package pg.context.auth.application.cqrs.query.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;
import pg.lib.cqrs.query.QueryHandler;

/**
 * The type User context query handler.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserContextQueryHandler implements QueryHandler<UserContextQuery, UserContext> {
    private final ContextService contextService;
    private final Cache contextCache;

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
                .orElseThrow(() -> new UserContextNotFoundException(contextToken));
    }
}