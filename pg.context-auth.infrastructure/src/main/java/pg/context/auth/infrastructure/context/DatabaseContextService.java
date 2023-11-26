package pg.context.auth.infrastructure.context;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.ContextService;
import pg.context.auth.domain.user.User;
import pg.context.auth.infrastructure.cache.CacheNames;

import java.util.Optional;

/**
 * The type Database context service.
 */
@Log4j2
@Service
public class DatabaseContextService implements ContextService {
    private final ContextRepository contextRepository;
    private final ContextSecurityService contextSecurityService;
    private final Cache contextCache;

    /**
     * Instantiates a new Database context service.
     *
     * @param contextRepository      the context repository
     * @param contextSecurityService the context security service
     * @param contextCache           the context cache
     */
    public DatabaseContextService(final ContextRepository contextRepository,
                                  final ContextSecurityService contextSecurityService,
                                  final @Qualifier(CacheNames.CONTEXT_CACHE) Cache contextCache) {
        this.contextRepository = contextRepository;
        this.contextSecurityService = contextSecurityService;
        this.contextCache = contextCache;
    }

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
    }

    private void signContext(final @NonNull ContextEntity context) {
        context.setSerialId(contextSecurityService.getSerialId());
        context.setContextToken(contextSecurityService.generateContextToken());
    }
}
