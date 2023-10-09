package ch.vkaelin.music.domain.file;

import java.io.InputStream;

public interface FileAdapter {
    void save(String fileName, InputStream inputStream) throws FileAdapterException;

    InputStream load(String fileName) throws FileAdapterException;

    void delete(String fileName);
}
