package pg.context.auth.spring.delivery.http.cqrs;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pg.context.auth.application.cqrs.query.handlers.UserContextQuery;
import pg.context.auth.domain.context.UserContext;
import pg.lib.cqrs.service.ServiceExecutor;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

import static pg.context.auth.spring.delivery.http.common.HttpCommonHelper.CQRS_PATH;

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
    private final RemoteCqrsModuleServiceExecutor remoteExecutor;

    /**
     * Execute user context.
     *
     * @param query the query
     * @return the user context
     */
    @PostMapping("/UserContextQuery")
    public UserContext execute(final @Valid @NonNull @RequestBody UserContextQuery query) {
        log.debug("Started execution of UserContextQuery: {}", query);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("/test/cqrs")
    public UserContext test(@RequestParam String token) {
        return this.remoteExecutor.execute(UserContextQuery.of(token), "users");
    }
}