package pg.context.auth.api.context.provider.remote;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import pg.context.auth.domain.context.ClearRemoteContextsCacheMessage;
import pg.kafka.consumer.MessageHandler;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class ClearRemoteContextsCacheMessageHandler implements MessageHandler<ClearRemoteContextsCacheMessage> {
    private final String applicationName;
    private final Cache contextCache;

    @Override
    public void handleMessage(final @NonNull ClearRemoteContextsCacheMessage message) {
        log.debug("Clearing remote contexts cache for tokens: {}", message.getContextTokens());
        message.getContextTokens().forEach(contextCache::evict);
    }

    @Override
    public Class<ClearRemoteContextsCacheMessage> getMessageType() {
        return ClearRemoteContextsCacheMessage.class;
    }

    @Override
    public Optional<String> getConsumerGroup() {
        return Optional.of(applicationName + "-ClearRemoteContextsCacheMessageHandler");
    }
}
