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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final ArtistService artistService;
    private final SongService songService;

    @PostMapping()
    @Secured("ARTIST")
    public void uploadSong(
            @ModelAttribute NewSongRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        String username = userDetails.getUsername();
        Artist artist = artistService.findByUsername(username).orElseThrow();

        if (!Objects.equals(request.getFile().getContentType(), "audio/mpeg")) {
            throw new IllegalArgumentException("File must be a mp3 file");
        }

        songService.createSong(request, artist);
    }

    @GetMapping("{songId}")
    public ResponseEntity<?> downloadSong(@PathVariable("songId") Integer id) {
        Optional<Song> song = songService.getSong(id);

        if (song.isEmpty()) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }

        String fileName = song.get().getFile();
        InputStreamResource resource = new InputStreamResource(songService.loadSong(fileName));

        String headerValue = "attachment; filename=\"" + fileName + "\"";
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
