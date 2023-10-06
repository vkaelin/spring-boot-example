package ch.vkaelin.music.domain.artist;

import ch.vkaelin.music.domain.file.FileAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistStorage artistStorage;
    private final FileAdapter fileAdapter;

    public Optional<Artist> findByUsername(String username) {
        return artistStorage.findByUsername(username);
    }

    public List<Artist> searchArtists(String search) {
        return artistStorage.searchArtists(search);
    }

    public void deleteArtist(Integer id) {
        var songs = artistStorage.findById(id).get().getSongs();
        songs.forEach(song -> fileAdapter.delete(song.getFile()));

        artistStorage.deleteArtist(id);
    }
}
