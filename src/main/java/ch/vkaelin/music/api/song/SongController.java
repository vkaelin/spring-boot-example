package ch.vkaelin.music.api.song;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.artist.ArtistService;
import ch.vkaelin.music.domain.song.Song;
import ch.vkaelin.music.domain.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final ArtistService artistService;
    private final SongService songService;

    @PostMapping()
    @Secured("ARTIST")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadSong(
            @ModelAttribute NewSongRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        Artist artist = artistService.findByUsername(username);
        Song song = songService.createSong(request, artist);

        return "Song created with id " + song.getId();
    }

    @GetMapping("{songId}")
    public ResponseEntity<?> downloadSong(@PathVariable("songId") Integer id) {
        Song song = songService.findById(id);
        InputStreamResource resource = new InputStreamResource(songService.loadSong(song.getFile()));

        String headerValue = "attachment; filename=\"" + song.getFile() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SearchedSongDto> searchSongs(@RequestBody Optional<String> search) {
        List<Song> songs = songService.searchSongs(search.orElse(""));
        return SearchedSongDto.from(songs);
    }
}
