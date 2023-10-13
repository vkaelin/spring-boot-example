package ch.vkaelin.music.integration.http.joke.response;

import lombok.Getter;

@Getter
public abstract class JokeResponse {
    boolean error;
    String category;
    String type;
    Flags flags;
    int id;
    boolean safe;
    String lang;
}
