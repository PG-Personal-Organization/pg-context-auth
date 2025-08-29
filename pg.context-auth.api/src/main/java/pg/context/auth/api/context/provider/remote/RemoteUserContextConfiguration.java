package pg.context.auth.api.context.provider.remote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

import java.util.Objects;

@EnableCaching
@Configuration
public class RemoteUserContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContextProvider httpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor, final CacheManager cacheManager, final HeadersHolder headersHolder) {
        var contextCache = Objects.requireNonNull(cacheManager.getCache("contextCache"));
        return new HttpUserContextProvider(serviceExecutor, contextCache, headersHolder);
    }
}
