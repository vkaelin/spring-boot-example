package ch.vkaelin.music.configuration;

import ch.vkaelin.music.domain.auth.JwtAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtAdapter {
    @Value("${config.jwt.expiration}")
    private Long expiration;

    private final JwtEncoder encoder;

    @Override
    public String generateToken(UserDetails userDetails) {
        return this.encoder.encode(
                JwtEncoderParameters.from(getClaims(userDetails))
        ).getTokenValue();
    }

    private String getScope(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private JwtClaimsSet getClaims(UserDetails userDetails) {
        Instant now = Instant.now();

        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .subject(userDetails.getUsername())
                .claim("scope", getScope(userDetails))
                .build();
    }
}
