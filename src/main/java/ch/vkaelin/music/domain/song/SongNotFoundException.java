package ch.vkaelin.music.domain.song;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException() {
        super("Song not found");
    }
}
