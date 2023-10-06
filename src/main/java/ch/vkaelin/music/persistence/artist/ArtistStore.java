package ch.vkaelin.music.persistence.artist;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.artist.ArtistStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArtistStore implements ArtistStorage {
    private final ArtistRepository artistRepository;

    @Override
    public Optional<Artist> findById(Integer id) {
        return artistRepository.findById(id)
                .map(ArtistEntity::fromThis);
    }

    @Override
    public Optional<Artist> findByUsername(String username) {
        return artistRepository.findByUserUsername(username)
                .map(ArtistEntity::fromThis);
    }

    @Override
    public void save(Artist artist) {
        ArtistEntity artistEntity = ArtistEntity.from(artist);
        artistRepository.save(artistEntity);
    }

    @Override
    public List<Artist> searchArtists(String search) {
        return artistRepository.search(search).stream()
                .map(ArtistEntity::fromThis)
                .toList();
    }

    @Override
    public void deleteArtist(Integer id) {
        artistRepository.deleteById(id);
    }
}


