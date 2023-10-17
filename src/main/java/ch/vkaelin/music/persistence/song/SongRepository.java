package ch.vkaelin.music.persistence.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<SongEntity, Integer> {
    @Query("SELECT s FROM SongEntity s JOIN s.artist a WHERE s.name ILIKE %?1%"
            + " OR a.artistName ILIKE %?1%"
            + " OR s.genre ILIKE %?1%")
    List<SongEntity> search(String keyword);

    Optional<SongEntity> findByFile(String file);
}
