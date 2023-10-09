package ch.vkaelin.music.domain.song;

import ch.vkaelin.music.domain.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Song {
    private final Integer id;
    private final String name;
    private final String file;
    private final String genre;
    private final Artist artist;
}
