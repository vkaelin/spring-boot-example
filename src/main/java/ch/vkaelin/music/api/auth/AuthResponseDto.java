package ch.vkaelin.music.api.auth;

import ch.vkaelin.music.integration.http.joke.JokeAPI;
import ch.vkaelin.music.integration.http.joke.JokeMapper;
import lombok.Value;

@Value
public class AuthResponseDto {
    String token;
    String joke = JokeMapper.fromRaw(JokeAPI.build().getTwoPartJoke());
}
