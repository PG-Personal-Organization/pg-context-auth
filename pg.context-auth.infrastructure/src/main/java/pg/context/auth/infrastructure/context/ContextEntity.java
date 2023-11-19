package pg.context.auth.infrastructure.context;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
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
    private String id;
    private String userId;
    private String username;
    @ElementCollection(targetClass = String.class)
    private Set<String> roles;

    @NonNull @NotEmpty private String serialId;
    @NonNull @NotEmpty private String contextToken;
}