package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedArtistDto> searchArtists(@RequestBody Optional<String> search) {
        List<Artist> artists = service.searchArtists(search.orElse(""));
        return SearchedArtistDto.from(artists);
    }

    @Secured("ADMIN")
    @DeleteMapping("{artistId}")
    public void deleteArtist(@PathVariable("artistId") Integer id) {
        service.deleteArtist(id);
    }
}
