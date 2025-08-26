package pg.context.auth.api.context.provider.remote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

@Import({
        RemoteSufficientContextCacheConfiguration.class,
        RemoteIntegratedContextCacheConfiguration.class
})
@Configuration
public class RemoteUserContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpUserContextProvider httpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor, final Cache contextCache, final HeadersHolder headersHolder) {
        return new HttpUserContextProvider(serviceExecutor, contextCache, headersHolder);
    }
}
