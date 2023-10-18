package ch.vkaelin.music.integration.http.joke.response;

import lombok.Getter;

@Getter
public class Flags {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;
    private boolean explicit;
}
