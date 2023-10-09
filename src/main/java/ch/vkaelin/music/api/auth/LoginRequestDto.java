package ch.vkaelin.music.api.auth;

import lombok.Value;

@Value
public class LoginRequestDto {
    String username;
    String password;
}
