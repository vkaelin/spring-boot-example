package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.api.song.SongDto;
import ch.vkaelin.music.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@Value
public class SearchedArtistDto {
    Integer id;
    String artistName;
    List<SongDto> songs;

    public static SearchedArtistDto from(Artist artist) {
        return new SearchedArtistDto(
                artist.getId(),
                artist.getArtistName(),
                SongDto.from(artist.getSongs())
        );
    }

    public static List<SearchedArtistDto> from(List<Artist> artists) {
        return artists.stream()
                .map(SearchedArtistDto::from)
                .toList();
    }
}
