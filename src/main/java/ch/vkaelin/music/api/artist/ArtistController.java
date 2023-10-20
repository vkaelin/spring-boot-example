package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.domain.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistMapper mapper;
    private final ArtistService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedArtistDto> searchArtists(@RequestParam(defaultValue = "") String search) {
        return mapper.toSearchedArtistDto(service.searchArtists(search));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{artistId}")
    public void deleteArtist(@PathVariable("artistId") Integer id) {
        service.deleteArtist(id);
    }
}
