package ch.vkaelin.music.api.auth;

import ch.vkaelin.music.domain.auth.AuthRequest;
import ch.vkaelin.music.domain.auth.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    AuthRequest toAuthRequest(LoginRequestDto request);

    SignUpRequest toSignUpRequest(SignUpRequestDto request);
}
