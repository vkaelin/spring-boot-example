package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.domain.artist.Artist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    SearchedArtistDto toSearchedArtistDto(Artist artist);

    List<SearchedArtistDto> toSearchedArtistDto(List<Artist> artists);
}

