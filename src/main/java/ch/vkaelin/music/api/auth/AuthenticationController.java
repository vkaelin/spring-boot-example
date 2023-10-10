package ch.vkaelin.music.api.auth;

import ch.vkaelin.music.domain.auth.AuthRequest;
import ch.vkaelin.music.domain.auth.AuthenticationService;
import ch.vkaelin.music.domain.auth.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthenticationMapper mapper;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto register(@RequestBody SignUpRequestDto request) {
        SignUpRequest signupRequest = mapper.toSignUpRequest(request);
        return new AuthResponseDto(service.register(signupRequest));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto authenticate(@RequestBody LoginRequestDto request) {
        AuthRequest authRequest = mapper.toAuthRequest(request);
        return new AuthResponseDto(service.authenticate(authRequest));
    }
}
