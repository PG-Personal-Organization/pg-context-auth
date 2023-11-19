package pg.context.auth.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserService;

import java.util.List;

/**
 * The type User repository service.
 */
@Service
@RequiredArgsConstructor
public class UserRepositoryService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::fromEntity)
                .orElse(null);
    }

    @Override
    public User findById(final String id) {
        return userRepository.findById(id)
                .map(UserMapper::fromEntity)
                .orElse(null);
    }

    @Override
    public String addUser(final User user) {
        UserEntity entity = UserMapper.fromDto(user);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity)
                .getId();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::fromEntity)
                .toList();
    }
}
