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
    private final ArtistEntityMapper artistEntityMapper;

    @Override
    public Optional<Artist> findById(Integer id) {
        return artistRepository.findById(id)
                .map(artistEntityMapper::toDomainWithSongs);
    }

    @Override
    public Optional<Artist> findByUsername(String username) {
        return artistRepository.findByUserUsername(username)
                .map(artistEntityMapper::toDomain);
    }

    @Override
    public void save(Artist artist) {
        ArtistEntity artistEntity = artistEntityMapper.toEntity(artist);
        // Had to flush here to get the error in the transaction if the username is already taken
        artistRepository.saveAndFlush(artistEntity);
    }

    @Override
    public List<Artist> searchArtists(String search) {
        return artistRepository.search(search).stream()
                .map(artistEntityMapper::toDomainWithSongs)
                .toList();
    }

    @Override
    public void deleteArtist(Integer id) {
        artistRepository.deleteById(id);
    }
}


