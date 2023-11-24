package pg.context.auth.api.context.provider.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

import java.util.Optional;

/**
 * The type Http user context provider.
 */
@Log4j2
@RequiredArgsConstructor
public class HttpUserContextProvider implements ContextProvider<UserContext> {
    private final RemoteCqrsModuleServiceExecutor serviceExecutor;

    @Override
    public Optional<UserContext> tryToGetUserContext(final String contextToken) {
        UserContext result = null;
        var query = toUserContextQuery(contextToken);

        try {
            result = serviceExecutor.execute(query, ModuleName.NAME, 1);
            log.info("Fetching context {} ended successfully", result);
        } catch (final Exception e) {
            log.error("Error appeared when trying to fetch context", e);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public UserContext getUserContext(final String contextToken) throws UserContextNotFoundException {
        UserContext result;
        var query = toUserContextQuery(contextToken);

        try {
            result = serviceExecutor.execute(query, ModuleName.NAME, 1);
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
