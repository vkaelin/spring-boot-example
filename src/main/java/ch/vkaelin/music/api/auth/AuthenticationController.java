package ch.vkaelin.music.api.auth;

import ch.vkaelin.music.domain.auth.AuthRequest;
import ch.vkaelin.music.domain.auth.AuthenticationService;
import ch.vkaelin.music.domain.auth.SignUpRequest;
import ch.vkaelin.music.integration.mail.templates.NewUserMail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthenticationMapper mapper;
    private final NewUserMail newUserMail;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto register(@Valid @RequestBody SignUpRequestDto request) {
        SignUpRequest signupRequest = mapper.toSignUpRequest(request);
        String token = service.register(signupRequest);
        BackgroundJob.enqueue(() -> newUserMail.sendMail(request.getUsername()));
        return new AuthResponseDto(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto authenticate(@Valid @RequestBody LoginRequestDto request) {
        AuthRequest authRequest = mapper.toAuthRequest(request);
        return new AuthResponseDto(service.authenticate(authRequest));
    }
}
