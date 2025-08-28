package pg.context.auth.api.context.provider.remote;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Currently unused
 */
@ConditionalOnBean(CacheManager.class)
@EnableCaching
@Configuration
public class RemoteIntegratedContextCacheConfiguration {
    private static final String CONTEXT_CACHE = "contextCache";

    @Bean
    @Primary
    public CacheManager integratedCacheManager(final CacheManager cacheManager) {
        var libraryFallback = new SimpleCacheManager();
        libraryFallback.setCaches(List.of(new ConcurrentMapCache(CONTEXT_CACHE)));
        libraryFallback.initializeCaches();

        var composite = new CompositeCacheManager(cacheManager, libraryFallback);
        composite.setFallbackToNoOpCache(false);
        return composite;
    }

    @Bean
    @Qualifier(CONTEXT_CACHE)
    public Cache contextCache(final CacheManager cacheManager) {
        return cacheManager.getCache(CONTEXT_CACHE);
    }
}
