package ch.vkaelin.music.api.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SignUpRequestDto {
    @NotNull(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    String username;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character")
    String password;

    @NotNull(message = "Artist name is required")
    @Size(min = 4, max = 30, message = "Artist name must be between 4 and 30 characters")
    String artistName;
}
