package ch.vkaelin.music.domain.song;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class NewSongRequest {
    String name;
    String genre;
    MultipartFile file;
}
