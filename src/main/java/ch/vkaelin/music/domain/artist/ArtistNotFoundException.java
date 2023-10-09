package ch.vkaelin.music.domain.artist;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException() {
        super("Artist not found");
    }
}
