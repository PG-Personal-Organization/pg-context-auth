package pg.context.auth.infrastructure.context;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.cache.Cache;
import pg.context.auth.domain.context.ClearRemoteContextsCacheMessage;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.user.User;
import pg.kafka.sender.EventSender;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Database context service.
 */
@Log4j2
@RequiredArgsConstructor
public class DatabaseContextService implements ContextService {
    private final ContextRepository contextRepository;
    private final ContextSecurityService contextSecurityService;
    private final Cache contextCache;
    private final EventSender eventSender;

    @Override
    public Optional<UserContext> findByToken(final @NonNull String contextToken) {
        if (contextCache != null) {
            var userContext = contextCache.get(contextToken, UserContext.class);
            if (userContext != null) {
                return Optional.of(userContext);
            }
        }

        return contextRepository.findFirstByContextToken(contextToken)
                .filter(contextSecurityService::checkSerialId)
                .map(ContextMapper::fromEntity)
                .or(Optional::empty);
    }

    @Override
    @Transactional
    public String createContextForUser(final @NonNull User user) {
        final ContextEntity context = ContextMapper.fromUser(user);
        signContext(context);
        clearContextsOfUser(user);

        contextRepository.save(context);

        val token = context.getContextToken();
        contextCache.put(token, ContextMapper.fromEntity(context));
        log.info("Created context for user: {} with token: {}", user, token);
        return token;
    }

    @Override
    @Transactional
    public void removeContext(final @NonNull String contextToken) {
        val contextBox = contextRepository.findFirstByContextToken(contextToken);
        if (contextBox.isPresent()) {
            log.info("Removing context with token: {}", contextToken);
            contextRepository.delete(contextBox.get());
            contextCache.evict(contextToken);
            eventSender.sendEvent(ClearRemoteContextsCacheMessage.of(Collections.singleton(contextToken)));
        }
    }

    @Override
    @Transactional
    public void clearContextsOfUser(final @NonNull User user) {
        val contexts = contextRepository.findAllByUserId(user.getId());

        if (!contexts.isEmpty()) {
            log.info("Deleting old contexts in number:{} for user: {}", contexts.size(), user);
            contextRepository.deleteAllInBatch(contexts);
            contexts.forEach(context -> contextCache.evict(context.getContextToken()));
            eventSender.sendEvent(ClearRemoteContextsCacheMessage.of(contexts.stream().map(ContextEntity::getContextToken).collect(Collectors.toSet())));
        } else {
            log.info("No previous user contexts found");
        }
    }

    @Override
    @Transactional
    public void clearOldContexts() {
        val deprecatedContexts = contextRepository.findAllBySerialIdIsNot(contextSecurityService.getSerialId());

        log.info("Cleaning deprecatedContexts: {}", deprecatedContexts);
        contextRepository.deleteAllInBatch(deprecatedContexts);
        deprecatedContexts.forEach(context -> contextCache.evict(context.getContextToken()));
        eventSender.sendEvent(ClearRemoteContextsCacheMessage.of(deprecatedContexts.stream().map(ContextEntity::getContextToken).collect(Collectors.toSet())));
    }

    private void signContext(final @NonNull ContextEntity context) {
        context.setSerialId(contextSecurityService.getSerialId());
        context.setContextToken(contextSecurityService.generateContextToken());
    }
}
