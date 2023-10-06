package ch.vkaelin.music.integration.file;

import ch.vkaelin.music.domain.file.FileAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStore implements FileAdapter {
    private static final String path = "./songs";

    @Override
    public void save(String fileName, InputStream inputStream) {
        Path path = Paths.get(FileStore.path, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.copy(inputStream, path);
        } catch (IOException e) {
            // TODO
            System.out.println(e.getMessage());
        }
    }

    @Override
    public InputStream load(String fileName) {
        Path path = Paths.get(FileStore.path, fileName);
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            // TODO
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String fileName) {
        Path path = Paths.get(FileStore.path, fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            // TODO
            System.out.println(e.getMessage());
        }
    }
}
