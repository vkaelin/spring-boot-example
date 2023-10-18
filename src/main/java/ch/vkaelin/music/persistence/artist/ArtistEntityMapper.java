package ch.vkaelin.music.persistence.artist;

import ch.vkaelin.music.domain.artist.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("ArtistEntityMapper")
@Mapper(componentModel = "spring")
public interface ArtistEntityMapper {
    @Mapping(target = "songs", ignore = true)
    ArtistEntity toEntity(Artist artist);

    @Mapping(target = "songs", ignore = true)
    Artist toDomain(ArtistEntity entity);

    @Named("toDomainWithSongs")
    Artist toDomainWithSongs(ArtistEntity entity);
}
