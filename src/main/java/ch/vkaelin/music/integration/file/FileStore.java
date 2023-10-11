package ch.vkaelin.music.integration.file;

import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.domain.file.FileAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class FileStore implements FileAdapter {
    @Value("${config.file.base-path}")
    private String basePath;

    @Override
    public List<String> listFiles() throws FileAdapterException {
        try (var files = Files.walk(Paths.get(basePath))) {
            return files
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        } catch (IOException e) {
            throw new FileAdapterException("Could not list files", e);
        }
    }

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
        Path path = Paths.get(basePath, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.copy(inputStream, path);
        } catch (IOException e) {
            throw new FileAdapterException("Could not save file " + fileName, e);
        }
    }

    @Override
    public InputStream load(String fileName) throws FileAdapterException {
        Path path = Paths.get(basePath, fileName);
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileAdapterException("Could not load file " + fileName, e);
        }
    }

    @Override
    public void delete(String fileName) {
        Path path = Paths.get(basePath, fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Could not delete file " + fileName, e);
        }
    }
}
