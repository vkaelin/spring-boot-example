package ch.vkaelin.music.persistence.song;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.song.Song;
import ch.vkaelin.music.persistence.artist.ArtistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SongEntityMapper {
    SongEntity toEntity(Song song);

    @Mapping(target = "artist", qualifiedByName = "artistToDomainWithoutSongs")
    Song toDomain(SongEntity entity);

    @Named("artistToDomainWithoutSongs")
    @Mapping(target = "songs", ignore = true)
    Artist artistToDomainWithoutSongs(ArtistEntity entity);

    @Named("toDomainWithoutArtist")
    @Mapping(target = "artist", ignore = true)
    Song toDomainWithoutArtist(SongEntity entity);
}
