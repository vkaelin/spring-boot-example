package ch.vkaelin.music.domain.song;

import ch.vkaelin.music.domain.ResourceNotFoundException;

public class SongNotFoundException extends ResourceNotFoundException {
    public SongNotFoundException() {
        super("Song not found");
    }
}
