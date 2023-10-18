package ch.vkaelin.music.persistence.song;

import ch.vkaelin.music.domain.song.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SongEntityMapper {
    SongEntity toEntity(Song song);

    @Mapping(target = "artist", ignore = true)
    Song toDomain(SongEntity entity);

    @Named("toDomainWithArtist")
    Song toDomainWithArtist(SongEntity entity);
}
