package pg.context.auth.spring.configuration.auth;

import lombok.Getter;

/**
 * The enum Jwt expire.
 */
@Getter
public enum JwtExpire {
    /**
     * Access token jwt expire.
     */
    ACCESS_TOKEN(4 * 60 * 1000),
    /**
     * Refresh token jwt expire.
     */
    REFRESH_TOKEN(12 * 60 * 60 * 1000);

    private final Integer amount;

    JwtExpire(Integer amount) {
        this.amount = amount;
    }
}
