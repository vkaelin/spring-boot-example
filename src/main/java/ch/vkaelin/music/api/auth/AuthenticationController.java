package ch.vkaelin.music.api.auth;

import ch.vkaelin.music.domain.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto register(@RequestBody SignUpRequestDto request) {
        return service.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto authenticate(@RequestBody LoginRequestDto request) {
        return service.authenticate(request);
    }
}
