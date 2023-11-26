package pg.context.auth.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.api.frontend.HttpEndpointPaths;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.auth.HeaderAuthenticationFilter;
import pg.lib.common.spring.auth.HeaderNames;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSecurityFilter extends HeaderAuthenticationFilter {
    private final ContextProvider<UserContext> contextProvider;

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
                                    final @NonNull HttpServletResponse response,
                                    final @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String contextToken = request.getHeader(HeaderNames.CONTEXT_TOKEN);

        if (!(contextToken == null || contextToken.isBlank())) {
            Optional<UserContext> userContext = contextProvider.tryToGetUserContext(contextToken);

            if (userContext.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        String.format("Context with token: %s not found, please login again.", contextToken));
            }

            var context = userContext.get();
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = context.getRoles().stream()
                    .map(this::toGrantedAuthority)
                    .collect(Collectors.toUnmodifiableSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    context.getUsername(), null, simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    String.format("Authorization header: %s has been not provided", HeaderNames.CONTEXT_TOKEN));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String path = request.getRequestURI();
        return HttpEndpointPaths.LOGIN.equals(path);
    }

    private SimpleGrantedAuthority toGrantedAuthority(final @NonNull String role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
