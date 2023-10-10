package ch.vkaelin.music.api.artist;

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
    private final ArtistMapper mapper;
    private final ArtistService service;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedArtistDto> searchArtists(@RequestBody Optional<String> search) {
        return mapper.toSearchedArtistDto(
                service.searchArtists(search.orElse(""))
        );
    }

    @Secured("ADMIN")
    @DeleteMapping("{artistId}")
    public void deleteArtist(@PathVariable("artistId") Integer id) {
        service.deleteArtist(id);
    }
}
