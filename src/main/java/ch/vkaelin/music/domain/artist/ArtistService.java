package ch.vkaelin.music.domain.artist;

import ch.vkaelin.music.domain.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {
    private final ArtistStorage artistStorage;
    private final SongService songService;

    public Artist findByUsername(String username) throws ArtistNotFoundException {
        return artistStorage.findByUsername(username)
                .orElseThrow(ArtistNotFoundException::new);
    }

    public List<Artist> searchArtists(String search) {
        return artistStorage.searchArtists(search);
    }

    @Transactional
    public void deleteArtist(Integer id) throws ArtistNotFoundException {
        var songs = artistStorage.findById(id)
                .orElseThrow(ArtistNotFoundException::new)
                .getSongs();

        songs.forEach(songService::deleteSong);

        artistStorage.deleteArtist(id);
    }
}
