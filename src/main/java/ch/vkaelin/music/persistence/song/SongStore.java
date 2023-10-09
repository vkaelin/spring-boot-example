package ch.vkaelin.music.persistence.song;

import ch.vkaelin.music.domain.song.Song;
import ch.vkaelin.music.domain.song.SongStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SongStore implements SongStorage {
    private final SongRepository songRepository;

    @Override
    public Song save(Song song) {
        SongEntity songEntity = SongEntity.from(song);
        return songRepository.save(songEntity).fromThis(false);
    }

    @Override
    public Optional<Song> findById(Integer id) {
        Optional<SongEntity> songEntity = songRepository.findById(id);
        return songEntity.map(s -> s.fromThis(false));
    }

    @Override
    public List<Song> search(String search) {
        List<SongEntity> songEntities = songRepository.search(search);
        return songEntities.stream()
                .map(s -> s.fromThis(true))
                .toList();
    }
}
