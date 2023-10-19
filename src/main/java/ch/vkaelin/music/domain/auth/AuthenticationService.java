package ch.vkaelin.music.domain.auth;

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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {
    private final ArtistStorage artistStorage;
    private final UserStorage userStorage;
    private final PasswordEncoder passwordEncoder;
    private final JwtAdapter jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String register(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ARTIST)
                .build();

        Artist artist = Artist.builder()
                .artistName(request.getArtistName())
                .user(user)
                .build();

        try {
            artistStorage.save(artist);
        } catch (Exception e) {
            throw new UsernameTakenException();
        }

        return jwtService.generateToken(artist.getUser());
    }

    public String authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userStorage.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.generateToken(user);
    }
}
