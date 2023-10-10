package ch.vkaelin.music.integration.file;

import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.domain.file.FileAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileStore implements FileAdapter {
    private static final String BASE_PATH = "./songs";

    @Override
    public InputStream getStream(InputStreamSource file) throws FileAdapterException {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new FileAdapterException("Could not read file", e);
        }
    }

    @Override
    public void save(String fileName, InputStream inputStream) throws FileAdapterException {
        Path path = Paths.get(BASE_PATH, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.copy(inputStream, path);
        } catch (IOException e) {
            throw new FileAdapterException("Could not save file " + fileName, e);
        }
    }

    @Override
    public InputStream load(String fileName) throws FileAdapterException {
        Path path = Paths.get(BASE_PATH, fileName);
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileAdapterException("Could not load file " + fileName, e);
        }
    }

    @Override
    public void delete(String fileName) {
        Path path = Paths.get(BASE_PATH, fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Could not delete file " + fileName, e);
        }
    }
}
