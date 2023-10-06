package ch.vkaelin.music.domain.file;

import java.io.IOException;
import java.io.InputStream;

public interface FileAdapter {
    void save(String fileName, InputStream inputStream) throws IOException;

    InputStream load(String fileName);

    void delete(String fileName);
}
