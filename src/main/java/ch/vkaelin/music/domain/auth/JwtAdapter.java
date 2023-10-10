package ch.vkaelin.music.domain.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAdapter {
    String generateToken(UserDetails userDetails);
}
