package pg.context.auth.spring.delivery.http.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Http common helper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpCommonHelper {
    /**
     * The constant BASE_PATH.
     */
    public static final String BASE_PATH = "api";

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
