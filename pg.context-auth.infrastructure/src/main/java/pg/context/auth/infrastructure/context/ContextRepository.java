package pg.context.auth.infrastructure.context;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Context repository.
 */
@Repository
public interface ContextRepository extends JpaRepository<ContextEntity, UUID> {
    /**
     * Find first by context token optional.
     *
     * @param contextToken the context token
     * @return the optional
     */
    Optional<ContextEntity> findFirstByContextToken(String contextToken);

    /**
     * Find all by serial id is not list.
     *
     * @param serialId the serial id
     * @return the list
     */
    List<ContextEntity> findAllBySerialIdIsNot(String serialId);

    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<ContextEntity> findAllByUserId(String userId);
}
