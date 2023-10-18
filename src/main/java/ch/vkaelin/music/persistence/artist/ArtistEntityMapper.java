package ch.vkaelin.music.persistence.artist;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.persistence.song.SongEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("ArtistEntityMapper")
@Mapper(componentModel = "spring", uses = SongEntityMapper.class)
public interface ArtistEntityMapper {
    @Mapping(target = "songs", ignore = true)
    ArtistEntity toEntity(Artist artist);

    @Mapping(target = "songs", qualifiedByName = "toDomainWithoutArtist")
    Artist toDomain(ArtistEntity entity);
}
