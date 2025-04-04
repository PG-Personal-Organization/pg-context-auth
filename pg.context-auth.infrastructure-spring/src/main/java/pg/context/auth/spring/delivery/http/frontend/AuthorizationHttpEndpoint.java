package pg.context.auth.spring.delivery.http.frontend;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.context.auth.api.frontend.login.LoginRequest;
import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.user.UserAuthenticationService;
import pg.context.auth.domain.user.UserLoginRequest;
import pg.lib.common.spring.auth.HeaderNames;

import java.util.Optional;

import static pg.context.auth.api.frontend.HttpEndpointPaths.LOGIN_BASE;
import static pg.context.auth.api.frontend.HttpServicesPaths.AUTH_PATH;

/**
 * The type Authorization http endpoint.
 */
@Log4j2
@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
@Tag(name = "Authorization")
public class AuthorizationHttpEndpoint {
    private final UserAuthenticationService authenticationService;

    /**
     * Attempt authentication response entity.
     *
     * @param loginRequest the login request
     * @return the response entity
     */
    @PostMapping(LOGIN_BASE)
    public ResponseEntity<Void> attemptAuthentication(final @Valid @RequestBody LoginRequest loginRequest) {
        log.info("Processing login request for user: {}", loginRequest.getUsername());
        Optional<UserContext> result;

        try {
            result = authenticationService.tryToAuthenticate(UserLoginRequest.builder()
                    .username(loginRequest.getUsername())
                    .password(loginRequest.getPassword())
                    .build());
        } catch (final Exception e) {
           log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return result.<ResponseEntity<Void>>map(userContext -> ResponseEntity.ok()
                        .header(HeaderNames.CONTEXT_TOKEN, userContext.getContextToken())
                        .build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
