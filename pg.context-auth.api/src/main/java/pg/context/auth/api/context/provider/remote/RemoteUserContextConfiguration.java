package pg.context.auth.api.context.provider.remote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

@EnableCaching
@Configuration
public class RemoteUserContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpUserContextProvider httpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor, final Cache contextCache, final HeadersHolder headersHolder) {
        return new HttpUserContextProvider(serviceExecutor, contextCache, headersHolder);
    }
}
