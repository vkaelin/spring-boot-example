package ch.vkaelin.music.domain.user;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByUsername(String username);

    void save(User user);
}
