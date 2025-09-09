package pg.context.auth.domain.context;

import lombok.*;
import pg.kafka.message.Message;

import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ClearRemoteContextsCacheMessage extends Message {
    @NonNull
    private Set<String> contextTokens;
}
