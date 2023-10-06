package ch.vkaelin.music.domain.auth;

import ch.vkaelin.music.api.auth.LoginRequestDto;
import ch.vkaelin.music.api.auth.LoginResponseDto;
import ch.vkaelin.music.api.auth.SignUpRequestDto;
import ch.vkaelin.music.configuration.JwtService;
import ch.vkaelin.music.domain.artist.Artist;
import ch.vkaelin.music.domain.artist.ArtistStorage;
import ch.vkaelin.music.domain.user.Role;
import ch.vkaelin.music.domain.user.User;
import ch.vkaelin.music.domain.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ArtistStorage artistStorage;
    private final UserStorage userStorage;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto register(SignUpRequestDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ARTIST)
                .build();

        Artist artist = Artist.builder()
                .artistName(request.getArtistName())
                .user(user)
                .build();

        artistStorage.save(artist);
        String jwtToken = jwtService.generateToken(user);
        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public LoginResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userStorage.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
