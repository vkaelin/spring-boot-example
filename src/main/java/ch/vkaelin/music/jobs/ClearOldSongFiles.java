package ch.vkaelin.music.jobs;

import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.persistence.song.SongEntity;
import ch.vkaelin.music.persistence.song.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearOldSongFiles {
    private final SongRepository songRepository;
    private final FileAdapter fileAdapter;

    private boolean isFileUnused(String toFind, List<SongEntity> songs) {
        return songs.stream().noneMatch(song -> song.getFile().equals(toFind));
    }

    public void run() {
        List<SongEntity> songs = songRepository.findAll();
        List<String> fileNames = fileAdapter.listFiles();

        List<String> filesToRemove = fileNames.stream()
                .filter(fileName -> isFileUnused(fileName, songs))
                .toList();
        filesToRemove.forEach(fileAdapter::delete);

        if (filesToRemove.isEmpty()) {
            log.info("No files to remove.");
        } else {
            log.info("Removed {} files from a total of {}.", filesToRemove.size(), fileNames.size());
        }
    }
}
