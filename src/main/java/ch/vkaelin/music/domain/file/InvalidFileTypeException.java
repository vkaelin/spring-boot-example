package ch.vkaelin.music.domain.file;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
