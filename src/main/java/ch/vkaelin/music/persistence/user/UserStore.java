package ch.vkaelin.music.persistence.user;

import ch.vkaelin.music.domain.user.User;
import ch.vkaelin.music.domain.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStore implements UserStorage {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        var userEntity = userRepository.findByUsername(username);
        return userEntity.map(userEntityMapper::toDomain);
    }

    @Override
    public void save(User user) {
        var userEntity = userEntityMapper.toEntity(user);
        userRepository.save(userEntity);
    }
}
