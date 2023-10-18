package ch.vkaelin.music.domain.artist;

import ch.vkaelin.music.domain.ResourceNotFoundException;

public class ArtistNotFoundException extends ResourceNotFoundException {
    public ArtistNotFoundException() {
        super("Artist not found");
    }
}
