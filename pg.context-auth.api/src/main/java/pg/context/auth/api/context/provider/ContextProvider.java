package pg.context.auth.api.context.provider;

import pg.context.auth.domain.context.UserContext;
import pg.context.auth.domain.context.UserContextNotFoundException;

import java.util.Optional;

/**
 * The interface Context provider.
 *
 * @param <U> the type parameter
 */
public interface ContextProvider <U extends UserContext> {
     /**
      * Try to get user context optional.
      *
      * @param contextToken the context token
      * @return the optional
      */
     Optional<U> tryToGetUserContext(String contextToken);

     /**
      * Gets user context.
      *
      * @param contextToken the context token
      * @return the user context
      * @throws UserContextNotFoundException the user context not found exception
      */
     U getUserContext(String contextToken) throws UserContextNotFoundException;
}