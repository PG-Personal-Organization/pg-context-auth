package pg.context.auth.spring.configuration.app;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static pg.context.auth.infrastructure.cache.CacheNames.CONTEXT_CACHE;
import static pg.context.auth.infrastructure.cache.CacheNames.USERS_CACHE;

/**
 * The type Cache config.
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {
    /**
     * Cache manager cache manager.
     *
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CONTEXT_CACHE, USERS_CACHE);
    }

    /**
     * Context cache cache.
     *
     * @return the cache
     */
    @Bean
    @Qualifier(CONTEXT_CACHE)
    public Cache contextCache() {
        return cacheManager().getCache(CONTEXT_CACHE);
    }

    /**
     * Users cache cache.
     *
     * @return the cache
     */
    @Bean
    @Qualifier(USERS_CACHE)
    public Cache usersCache() {
        return cacheManager().getCache(USERS_CACHE);
    }

}