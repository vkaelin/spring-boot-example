package ch.vkaelin.music.domain.song;

import java.util.List;
import java.util.Optional;

public interface SongStorage {
    void save(Song song);

    Optional<Song> findById(Integer id);

    List<Song> search(String search);
}
