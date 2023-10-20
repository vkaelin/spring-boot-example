package ch.vkaelin.music.integration.http.joke.response;

import lombok.Getter;

@Getter
public abstract class JokeResponse {
    private boolean error;
    private String category;
    private String type;
    private Flags flags;
    private int id;
    private boolean safe;
    private String lang;
}
