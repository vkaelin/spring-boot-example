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

    @Override
    public Optional<User> findByUsername(String username) {
        var userEntity = userRepository.findByUsername(username);
        return userEntity.map(UserEntity::fromThis);
    }

    @Override
    public void save(User user) {
        var userEntity = UserEntity.from(user);
        userRepository.save(userEntity);
    }
}
