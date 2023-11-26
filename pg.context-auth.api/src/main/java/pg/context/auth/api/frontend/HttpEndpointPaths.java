package pg.context.auth.api.frontend;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Http common helper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpEndpointPaths {
    /**
     * The constant BASE_PATH.
     */
    public static final String LOGIN_BASE = "/login";
    /**
     * The constant BASE_PATH.
     */
    public static final String LOGIN = HttpServicesPaths.AUTH_PATH + LOGIN_BASE;

    /**
     * The constant BASE_PATH.
     */
    public static final String USER_CONTEXT_QUERY_BASE = "/UserContextQuery";
}
