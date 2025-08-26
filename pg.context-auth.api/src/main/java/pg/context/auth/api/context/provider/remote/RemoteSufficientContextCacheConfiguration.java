package pg.context.auth.api.context.provider.remote;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnMissingBean(CacheManager.class)
@EnableCaching
@Configuration
public class RemoteSufficientContextCacheConfiguration {
    private static final String CONTEXT_CACHE = "contextCache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CONTEXT_CACHE);
    }

    @Bean
    @Qualifier(CONTEXT_CACHE)
    public Cache contextCache() {
        return cacheManager().getCache(CONTEXT_CACHE);
    }
}
