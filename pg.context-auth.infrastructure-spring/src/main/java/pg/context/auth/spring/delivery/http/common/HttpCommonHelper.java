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
    public static final String BASE_PATH = "api/v1";

    /**
     * The constant AUTH_PATH.
     */
    public static final String AUTH_PATH = BASE_PATH + "/auth";

    /**
     * The constant USER_PATH.
     */
    public static final String USER_PATH = BASE_PATH + "/users";

}
