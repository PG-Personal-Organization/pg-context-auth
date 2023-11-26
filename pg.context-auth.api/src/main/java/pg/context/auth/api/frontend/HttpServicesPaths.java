package pg.context.auth.api.frontend;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Http services paths.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpServicesPaths {
    /**
     * The constant BASE_PATH.
     */
    public static final String BASE_PATH = "/api";

    /**
     * The constant AUTH_PATH.
     */
    public static final String AUTH_PATH = BASE_PATH + "/frontend/v1/auth";

    /**
     * The constant USER_PATH.
     */
    public static final String USER_PATH = BASE_PATH + "/frontend/v1/users";

    /**
     * The constant CQRS_PATH.
     */
    public static final String CQRS_PATH = BASE_PATH + "/cqrs/v1";

}
