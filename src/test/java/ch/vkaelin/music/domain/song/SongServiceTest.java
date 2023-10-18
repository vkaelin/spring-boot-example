package ch.vkaelin.music.domain.song;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.file.FileAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SongServiceTest {
    @Mock
    SongStorage songStorage;
    @Mock
    FileAdapter fileAdapter;

    @InjectMocks
    SongService songService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSong() throws IOException {
        // Given a name, a genre, a multipart file and an artist
        String songName = "Best Song Ever";
        String genre = "Pop";
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.mp3",
                "audio/mpeg",
                "test content".getBytes()
        );
        NewSongRequest request = new NewSongRequest(
                songName,
                genre,
                multipartFile
        );
        Artist artist = Artist.builder()
                .artistName("Bob")
                .id(1)
                .build();

        // When I create a song
        when(songStorage.save(any(Song.class))).thenAnswer(i -> i.getArgument(0));
        when(fileAdapter.getStream(any(InputStreamSource.class))).thenReturn(multipartFile.getInputStream());
        Song song = songService.createSong(request, artist);

        // Then I should get a song object and the song should be saved
        assertEquals(songName, song.getName());
        assertEquals(genre, song.getGenre());
        assertEquals(artist, song.getArtist());
        verify(fileAdapter, times(1)).save(any(String.class), any(InputStream.class));
    }

    @Test
    void shouldFindSongById() {
        // Given an id
        Integer id = 1;

        // When I search for a song
        when(songStorage.findById(id)).thenReturn(
                Optional.of(
                        Song.builder().id(id).build()
                )
        );

        // Then I should get the song back
        Song song = songService.findById(id);
        assertEquals(id, song.getId());
    }

    @Test
    void shouldThrowIfSongDoesntExist() {
        // Given a non-existing id
        Integer id = 42;

        // When I search for a song that doesn't exist
        when(songStorage.findById(id)).thenReturn(Optional.empty());

        // Then I should get an exception
        assertThrows(SongNotFoundException.class, () -> songService.findById(id));
    }
}
