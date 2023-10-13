package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.api.song.SongMapper;
import ch.vkaelin.music.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ArtistMapper {
    private final SongMapper songMapper;

    public ArtistDto toArtistDto(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getArtistName()
        );
    }

    public List<ArtistDto> toArtistDto(List<Artist> artists) {
        return artists.stream()
                .map(this::toArtistDto)
                .toList();
    }

    public SearchedArtistDto toSearchedArtistDto(Artist artist) {
        return new SearchedArtistDto(
                artist.getId(),
                artist.getArtistName(),
                songMapper.toSongDto(artist.getSongs())
        );
    }

    public List<SearchedArtistDto> toSearchedArtistDto(List<Artist> artists) {
        return artists.stream()
                .map(this::toSearchedArtistDto)
                .toList();
    }
}
