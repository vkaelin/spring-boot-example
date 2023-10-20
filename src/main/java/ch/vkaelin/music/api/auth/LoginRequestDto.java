package ch.vkaelin.music.api.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequestDto {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Password is required")
    String password;
}
