package pg.context.auth.spring.delivery.http.frontend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.context.auth.spring.configuration.auth.JwtTokenRefresher;

import static pg.context.auth.spring.delivery.http.common.HttpCommonHelper.AUTH_PATH;

/**
 * The type Authorization http endpoint.
 */
@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
@Tag(name = "Authorization")
public class AuthorizationHttpEndpoint {
    private final JwtTokenRefresher tokenRefresher;

    /**
     * Refresh access token.
     *
     * @param request  the request
     * @param response the response
     */
    @PostMapping("refresh-access")
    public void refreshAccessToken(final HttpServletRequest request, final HttpServletResponse response) {
        tokenRefresher.attemptRefreshToken(request, response);
    }
}
