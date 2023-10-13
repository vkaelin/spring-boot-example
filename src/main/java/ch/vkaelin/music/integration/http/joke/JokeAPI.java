package ch.vkaelin.music.integration.http.joke;

import ch.vkaelin.music.integration.http.joke.response.JokeResponse;
import ch.vkaelin.music.integration.http.joke.response.JokeSingle;
import ch.vkaelin.music.integration.http.joke.response.JokeTwoPart;
import feign.Feign;
import feign.RequestLine;

public interface JokeAPI {
    @RequestLine("GET /joke/Any")
    JokeResponse getAnyJoke();

    @RequestLine("GET /joke/Any?type=twopart")
    JokeTwoPart getTwoPartJoke();

    @RequestLine("GET /joke/Any?type=single")
    JokeSingle getSingleJoke();

    static JokeAPI build() {
        return Feign.builder()
                .decoder(new JokeGsonDecoder())
                .target(JokeAPI.class, "https://v2.jokeapi.dev");
    }
}
