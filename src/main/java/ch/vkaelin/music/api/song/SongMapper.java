package ch.vkaelin.music.api.song;

import ch.vkaelin.music.domain.song.NewSongRequest;
import ch.vkaelin.music.domain.song.Song;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SongDto toSongDto(Song song);

    SearchedSongDto toSearchSongDto(Song song);

    List<SearchedSongDto> toSearchSongDto(List<Song> songs);

    NewSongRequest toNewSongRequest(NewSongRequestDto dto);
}
