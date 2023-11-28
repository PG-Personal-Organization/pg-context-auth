package pg.context.auth.api.frontend;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static pg.context.auth.api.frontend.HttpServicesPaths.CQRS_PATH;

/**
 * The type Http endpoint paths.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpEndpointPaths {

    /**
     * The constant LOGIN_BASE.
     */
    public static final String LOGIN_BASE = "/login";

    /**
     * The constant LOGIN.
     */
    public static final String LOGIN = HttpServicesPaths.AUTH_PATH + LOGIN_BASE;

    /**
     * The constant USER_CONTEXT_QUERY_BASE.
     */
    public static final String USER_CONTEXT_QUERY_BASE = "/UserContextQuery";


    /**
     * The constant USER_CONTEXT_QUERY.
     */
    public static final String USER_CONTEXT_QUERY = CQRS_PATH + USER_CONTEXT_QUERY_BASE;

    /**
     * The constant USER_CONTEXT_QUERY_BASE.
     */
    public static final String USER_QUERY_BASE = "/UserQuery";


    /**
     * The constant USER_QUERY.
     */
    public static final String USER_QUERY = CQRS_PATH + USER_QUERY_BASE;

    /**
     * The constant GET_USERS.
     */
    public static final String GET_USERS = HttpServicesPaths.USER_PATH;
}
