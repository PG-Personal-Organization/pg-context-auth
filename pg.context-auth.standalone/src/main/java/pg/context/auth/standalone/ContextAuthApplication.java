package pg.context.auth.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pg.context.auth.spring.ContextAuthModuleConfiguration;

/**
 * The type Context auth application.
 */
@SpringBootApplication
@Import({
        ContextAuthModuleConfiguration.class
})
public class ContextAuthApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ContextAuthApplication.class, args);
    }
}
