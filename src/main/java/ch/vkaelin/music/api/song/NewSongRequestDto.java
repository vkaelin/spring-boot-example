package ch.vkaelin.music.api.song;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class NewSongRequestDto {
    @NotNull(message = "Song name is required")
    @Size(min = 4, max = 30, message = "Song name must be between 4 and 30 characters")
    String name;

    @NotNull(message = "Genre is required")
    @Size(min = 4, max = 30, message = "Artist name must be between 4 and 30 characters")
    String genre;

    @NotNull(message = "Song file is required")
    MultipartFile file;
}
