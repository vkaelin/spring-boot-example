package ch.vkaelin.music.api.song;

import ch.vkaelin.music.domain.file.FileAdapterException;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Value
public class NewSongRequestDto {
    String name;
    String genre;
    MultipartFile file;

    public InputStream getFileData() throws FileAdapterException {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new FileAdapterException("Could not read file", e);
        }
    }
}
