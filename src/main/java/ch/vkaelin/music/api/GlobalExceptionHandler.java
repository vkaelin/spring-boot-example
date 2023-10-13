package ch.vkaelin.music.api;

import ch.vkaelin.music.domain.artist.ArtistNotFoundException;
import ch.vkaelin.music.domain.auth.UsernameTakenException;
import ch.vkaelin.music.domain.file.FileAdapterException;
import ch.vkaelin.music.domain.file.InvalidFileTypeException;
import ch.vkaelin.music.domain.song.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<Object> handleArtistNotFoundException(ArtistNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<Object> handleSongNotFoundException(SongNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileAdapterException.class)
    public ResponseEntity<Object> handleFileAdapterException(FileAdapterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<Object> handleInvalidFileTypeException(InvalidFileTypeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
