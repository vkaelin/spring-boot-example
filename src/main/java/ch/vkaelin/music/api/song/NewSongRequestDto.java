package ch.vkaelin.music.api.song;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class NewSongRequestDto {
    String name;
    String genre;
    MultipartFile file;
}
