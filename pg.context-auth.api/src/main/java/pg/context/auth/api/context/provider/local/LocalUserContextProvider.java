package pg.context.auth.api.context.provider.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;
import pg.lib.common.spring.auth.HeaderNames;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.cqrs.service.ServiceExecutor;

import java.util.Optional;

/**
 * The type Local user context provider.
 */
@Log4j2
@RequiredArgsConstructor
public class LocalUserContextProvider implements ContextProvider<UserContext> {
    private final ServiceExecutor serviceExecutor;
    private final HeadersHolder headersHolder;

    @Override
    public Optional<UserContext> tryToGetUserContext(final String contextToken) {
        UserContext result = null;
        var query = toUserContextQuery(contextToken);

        try {
            result = serviceExecutor.executeQuery(query);
            log.info("Fetching context {} ended successfully", result);
        } catch (final Exception e) {
            log.error("Error appeared when trying to fetch context", e);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserContext> tryToGetUserContext() {
        return headersHolder.tryToGetHeader(HeaderNames.CONTEXT_TOKEN).map(this::getUserContext);
    }

    @Override
    public UserContext getUserContext(final String contextToken) throws UserContextNotFoundException {
        UserContext result;
        var query = toUserContextQuery(contextToken);

        try {
            result = serviceExecutor.executeQuery(query);
        } catch (final Exception e) {
            throw new UserContextNotFoundException(contextToken);
        }

        log.info("Fetching context {} ended successfully", result);
        return result;
    }

    private UserContextQuery toUserContextQuery(final String contextToken) {
        return UserContextQuery.of(contextToken);
    }

}
