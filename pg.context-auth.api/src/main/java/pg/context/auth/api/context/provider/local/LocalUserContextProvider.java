package pg.context.auth.api.context.provider.local;

import pg.context.auth.api.context.provider.CachingContextProvider;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.cqrs.service.ServiceExecutor;

/**
 * The type Local user context provider.
 */
public class LocalUserContextProvider extends CachingContextProvider {
    private final ServiceExecutor serviceExecutor;

    @SuppressWarnings("checkstyle:HiddenField")
    public LocalUserContextProvider(final ServiceExecutor serviceExecutor, final HeadersHolder headersHolder) {
        super(null, headersHolder);
        this.serviceExecutor = serviceExecutor;
    }

    @Override
    protected UserContext executeQuery(final UserContextQuery query) {
        return serviceExecutor.executeQuery(query);
    }

}
