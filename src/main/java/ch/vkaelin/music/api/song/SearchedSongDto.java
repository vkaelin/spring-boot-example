package ch.vkaelin.music.api.song;

import ch.vkaelin.music.api.artist.ArtistDto;
import lombok.Value;

@Value
public class SearchedSongDto {
    Integer id;
    String name;
    String genre;
    ArtistDto artist;
}
