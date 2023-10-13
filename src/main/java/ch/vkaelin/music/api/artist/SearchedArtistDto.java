package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.api.song.SongDto;
import lombok.Value;

import java.util.List;

@Value
public class SearchedArtistDto {
    Integer id;
    String artistName;
    List<SongDto> songs;
}
