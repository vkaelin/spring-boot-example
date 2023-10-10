package ch.vkaelin.music.domain.file;

import org.springframework.core.io.InputStreamSource;

import java.io.InputStream;

public interface FileAdapter {
    InputStream getStream(InputStreamSource file) throws FileAdapterException;

    void save(String fileName, InputStream inputStream) throws FileAdapterException;

    InputStream load(String fileName) throws FileAdapterException;

    void delete(String fileName);
}
