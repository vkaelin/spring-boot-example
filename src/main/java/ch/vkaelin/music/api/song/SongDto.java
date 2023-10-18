package ch.vkaelin.music.api.song;

import lombok.Value;

@Value
public class SongDto {
    Integer id;
    String name;
    String genre;
    String file;
}
