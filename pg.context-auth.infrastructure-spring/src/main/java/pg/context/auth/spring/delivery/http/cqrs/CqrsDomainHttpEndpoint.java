package pg.context.auth.spring.delivery.http.cqrs;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pg.context.auth.api.cqrs.query.UserContextQuery;
import pg.context.auth.api.cqrs.query.UserQuery;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.user.User;
import pg.lib.cqrs.service.ServiceExecutor;

import static pg.context.auth.api.frontend.HttpEndpointPaths.USER_CONTEXT_QUERY_BASE;
import static pg.context.auth.api.frontend.HttpEndpointPaths.USER_QUERY_BASE;
import static pg.context.auth.api.frontend.HttpServicesPaths.CQRS_PATH;

/**
 * The type Cqrs domain http endpoint.
 */
@RestController
@RequestMapping(CQRS_PATH)
@RequiredArgsConstructor
@Log4j2
@Tag(name = "CQRS")
public class CqrsDomainHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    /**
     * Execute user context.
     *
     * @param query the query
     * @return the user context
     */
    @PostMapping(USER_CONTEXT_QUERY_BASE)
    public UserContext execute(final @Valid @NonNull @RequestBody UserContextQuery query) {
        log.info("Started execution of UserContextQuery: {}", query);
        return serviceExecutor.executeQuery(query);
    }

    /**
     * Execute user.
     *
     * @param query the query
     * @return the user
     */
    @PostMapping(USER_QUERY_BASE)
    public User execute(final @Valid @NonNull @RequestBody UserQuery query) {
        log.info("Started execution of UserQuery: {}", query);
        return serviceExecutor.executeQuery(query);
    }
}
