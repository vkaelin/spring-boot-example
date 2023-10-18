package ch.vkaelin.music.api.auth;

import lombok.Value;

@Value
public class SignUpRequestDto {
    String username;
    String password;
    String artistName;
}
