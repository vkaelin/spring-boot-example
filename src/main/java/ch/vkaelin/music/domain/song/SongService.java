package ch.vkaelin.music.domain.song;

import ch.vkaelin.music.api.song.NewSongRequestDto;
import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.domain.file.FileAdapterException;
import ch.vkaelin.music.domain.file.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SongService {
    private final SongStorage songStorage;
    private final FileAdapter fileAdapter;

    public Song createSong(NewSongRequestDto dto, Artist artist)
            throws FileAdapterException, InvalidFileTypeException {
        if (!Objects.equals(dto.getFile().getContentType(), "audio/mpeg")) {
            throw new InvalidFileTypeException("File must be a mp3 file");
        }

        String fileName = UUID.randomUUID() + ".mp3";
        fileAdapter.save(fileName, dto.getFileData());

        Song song = Song.builder()
                .name(dto.getName())
                .genre(dto.getGenre())
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

    public void deleteSong(Song song) {
        fileAdapter.delete(song.getFile());
    }

    public List<Song> searchSongs(String search) {
        return songStorage.search(search);
    }
}
