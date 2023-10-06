package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@Value
public class ArtistDto {
    Integer id;
    String artistName;

    public static ArtistDto from(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getArtistName()
        );
    }

    public static List<ArtistDto> from(List<Artist> artists) {
        return artists.stream()
                .map(ArtistDto::from)
                .toList();
    }
}
