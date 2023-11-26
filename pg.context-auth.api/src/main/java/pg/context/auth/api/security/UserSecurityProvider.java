package pg.context.auth.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.domain.context.UserContext;

/**
 * The type User security provider.
 */
//@Service
@RequiredArgsConstructor
public class UserSecurityProvider implements UserDetailsService {
    private final ContextProvider<UserContext> contextProvider;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return null;
    }
}
