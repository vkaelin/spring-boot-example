package ch.vkaelin.music.domain.artist;

import java.util.List;
import java.util.Optional;

public interface ArtistStorage {
    Optional<Artist> findById(Integer id);

    Optional<Artist> findByUsername(String username);

    void save(Artist artist);

    List<Artist> searchArtists(String search);

    void deleteArtist(Integer id);
}
