package ch.vkaelin.music.domain.auth;

import lombok.Value;

@Value
public class AuthRequest {
    String username;
    String password;
}
