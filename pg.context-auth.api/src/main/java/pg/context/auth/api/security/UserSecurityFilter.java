package pg.context.auth.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.server.ResponseStatusException;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.frontend.HttpEndpointPaths;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.auth.HeaderAuthenticationFilter;
import pg.lib.common.spring.storage.HeadersHolder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * The type User security filter.
 */
@Log4j2
public class UserSecurityFilter extends HeaderAuthenticationFilter {
    private final ContextProvider contextProvider;

    private final RequestMatcher requestMatcher = RequestMatchers.anyOf(
            antMatcher(HttpEndpointPaths.LOGIN),
            antMatcher("/"),
            antMatcher("/error"),
            antMatcher("/actuator/**"),
            antMatcher("/swagger-ui**"),
            antMatcher("/v3/api-docs/**"));

    @SuppressWarnings("checkstyle:HiddenField")
    public UserSecurityFilter(final @Lazy ContextProvider contextProvider, final @Lazy HeadersHolder headersHolder) {
        super(headersHolder);
        this.contextProvider = contextProvider;
    }

    @Override
    protected void continueFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain,
                                  final String authenticationToken) {
        Optional<UserContext> userContext = contextProvider.tryToGetUserContext(authenticationToken);

        if (userContext.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    String.format("Context with token: %s not found, please login again.", authenticationToken));
        }

        var context = userContext.get();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = context.getRoles().stream()
                .map(this::toGrantedAuthority)
                .collect(Collectors.toUnmodifiableSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                context.getUsername(), null, simpleGrantedAuthorities
        );

        log.debug("Setting security context: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(final @NonNull HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    private SimpleGrantedAuthority toGrantedAuthority(final @NonNull String role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
