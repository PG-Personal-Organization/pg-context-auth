package pg.context.auth.api.context.provider.remote;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import pg.context.auth.api.context.provider.CachingContextProvider;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

/**
 * The type Http user context provider.
 */
@Log4j2
public class HttpUserContextProvider extends CachingContextProvider {
    private final RemoteCqrsModuleServiceExecutor serviceExecutor;

    @SuppressWarnings("checkstyle:HiddenField")
    public HttpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor, final Cache contextCache, final HeadersHolder headersHolder) {
        super(contextCache, headersHolder);
        this.serviceExecutor = serviceExecutor;
    }


    @Override
    protected UserContext executeQuery(final UserContextQuery query) {
        return serviceExecutor.execute(query, ModuleName.NAME, 1);
    }
}
