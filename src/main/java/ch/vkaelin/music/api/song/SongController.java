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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final ArtistService artistService;
    private final SongService songService;
    private final SongMapper songMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ARTIST')")
    public String uploadSong(
            @ModelAttribute NewSongRequestDto request,
            Authentication authentication
    ) {
        Artist artist = artistService.findByUsername(authentication.getName());
        Song song = songService.createSong(songMapper.toNewSongRequest(request), artist);

        return "Song created with id " + song.getId();
    }

    @GetMapping("{songId}")
    public ResponseEntity<InputStreamResource> downloadSong(@PathVariable("songId") Integer id) {
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
        return songMapper.toSearchSongDto(songs);
    }
}
