package ch.vkaelin.music.domain.song;

import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.domain.file.FileAdapterException;
import ch.vkaelin.music.domain.file.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {
    private final SongStorage songStorage;
    private final FileAdapter fileAdapter;
    public static final String AUDIO_MPEG = "audio/mpeg";

    @Transactional
    public Song createSong(NewSongRequest request, Artist artist)
            throws FileAdapterException, InvalidFileTypeException {
        if (!Objects.equals(request.getFile().getContentType(), AUDIO_MPEG)) {
            throw new InvalidFileTypeException("File must be a mp3 file");
        }

        String fileName = UUID.randomUUID() + ".mp3";
        fileAdapter.save(fileName, fileAdapter.getStream(request.getFile()));

        Song song = Song.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .file(fileName)
                .artist(artist)
                .build();

        return songStorage.save(song);
    }

    public Song findById(Integer id) throws SongNotFoundException {
        return songStorage.findById(id)
                .orElseThrow(SongNotFoundException::new);
    }

    public InputStream loadSong(String fileName) throws FileAdapterException {
        return fileAdapter.load(fileName);
    }

    @Transactional
    public void deleteSong(Song song) {
        fileAdapter.delete(song.getFile());
    }

    public List<Song> searchSongs(String search) {
        return songStorage.search(search);
    }
}
