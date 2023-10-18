package ch.vkaelin.music.domain.auth;

import lombok.Value;

@Value
public class SignUpRequest {
    String username;
    String password;
    String artistName;
}