package pg.context.auth.api.context.provider.remote;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.lib.remote.cqrs.executors.RemoteCqrsModuleServiceExecutor;

@Configuration
public class RemoteUserContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpUserContextProvider httpUserContextProvider(final RemoteCqrsModuleServiceExecutor serviceExecutor) {
        return new HttpUserContextProvider(serviceExecutor);
    }
}
