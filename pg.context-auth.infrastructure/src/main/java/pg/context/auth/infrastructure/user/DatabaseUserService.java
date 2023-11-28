package pg.context.auth.infrastructure.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserService;

import java.util.List;
import java.util.UUID;

/**
 * The type Database user service.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cache usersCache;

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::fromEntity)
                .orElse(null);
    }

    @Override
    public User findById(final String id) {
        if (usersCache != null) {
            var user = usersCache.get(id, User.class);
            if (user != null) {
                return user;
            }
        }

        return userRepository.findById(UUID.fromString(id))
                .map(UserMapper::fromEntity)
                .orElse(null);
    }

    @Override
    @Transactional
    public String addUser(final User user) {
        UserEntity entity = UserMapper.fromDto(user);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepository.save(entity);
        UUID userId = entity.getId();

        usersCache.put(userId, UserMapper.fromEntity(entity));
        log.info("Created user: {} with id: {}", user, userId);
        return userId.toString();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::fromEntity)
                .toList();
    }
}
