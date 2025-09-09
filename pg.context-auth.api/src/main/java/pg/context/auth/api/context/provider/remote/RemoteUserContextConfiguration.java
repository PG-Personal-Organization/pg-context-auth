package pg.context.auth.api.context.provider.remote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.domain.context.ClearRemoteContextsCacheMessage;
import pg.kafka.message.MessageDestination;
import pg.kafka.topic.TopicName;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

import java.util.Objects;
import java.util.UUID;

@EnableCaching
@EnableKafka
@Configuration
@ConditionalOnProperty(name = "pg.context-auth.remote-context-provider.enabled", havingValue = "true", matchIfMissing = true)
public class RemoteUserContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContextProvider httpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor, final CacheManager cacheManager, final HeadersHolder headersHolder) {
        var contextCache = Objects.requireNonNull(cacheManager.getCache("contextCache"));
        return new HttpUserContextProvider(serviceExecutor, contextCache, headersHolder);
    }

    @Bean
    public ClearRemoteContextsCacheMessageHandler clearRemoteContextsCacheMessageHandler(final Environment environment, final CacheManager cacheManager) {
        var contextCache = Objects.requireNonNull(cacheManager.getCache("contextCache"));
        var applicationName = environment.getProperty("spring.application.name", UUID.randomUUID().toString());
        return new ClearRemoteContextsCacheMessageHandler(applicationName, contextCache);
    }

    @Bean
    public MessageDestination clearRemoteContextsCacheMessageDestination() {
        return MessageDestination.builder()
                .topic(TopicName.of("clear-remote-contexts-cache-topic"))
                .messageClass(ClearRemoteContextsCacheMessage.class)
                .build();
    }
}
