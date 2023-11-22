package pg.context.auth.infrastructure.context;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * The type Context entity.
 */
@Entity(name = "contexts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ContextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String username;
    @ElementCollection(targetClass = String.class)
    private Set<String> roles;

    private String serialId;
    private String contextToken;
}