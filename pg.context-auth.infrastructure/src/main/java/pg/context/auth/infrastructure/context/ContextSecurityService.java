package pg.context.auth.infrastructure.context;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Context security service.
 */
@Service
@Getter
public class ContextSecurityService {
    private final String serialId = UUID.randomUUID().toString();

    @Value("context-auth.token.prefix:CTX_1_")
    private String tokenPrefix;

    /**
     * Generate context token string.
     *
     * @return the string
     */
    protected String generateContextToken() {
        return tokenPrefix + UUID.randomUUID();
    }

    /**
     * Check serial id boolean.
     *
     * @param context the context
     * @return the boolean
     */
    protected boolean checkSerialId(final ContextEntity context) {
        return context.getSerialId().equals(this.serialId);
    }
}