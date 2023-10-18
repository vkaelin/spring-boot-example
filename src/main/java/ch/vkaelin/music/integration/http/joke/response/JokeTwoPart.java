package ch.vkaelin.music.integration.http.joke.response;

import lombok.Getter;

@Getter
public class JokeTwoPart extends JokeResponse {
    private String setup;
    private String delivery;
}
