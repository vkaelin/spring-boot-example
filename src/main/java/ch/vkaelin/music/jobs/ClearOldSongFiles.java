package ch.vkaelin.music.jobs;

import ch.vkaelin.music.domain.file.FileAdapter;
import ch.vkaelin.music.persistence.song.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearOldSongFiles implements JobAdapter {
    private final SongRepository songRepository;
    private final FileAdapter fileAdapter;

    @Override
    @Recurring(id = "clear-old-song-files", cron = "${config.jobs.clear-songs.cron}")
    @Job(name = "Clear old song files from disk")
    public void run() {
        List<String> fileNames = fileAdapter.listFiles();
        int nbDeleted = 0;

        for (String fileName : fileNames) {
            var song = songRepository.findByFile(fileName);
            if (song.isEmpty()) {
                fileAdapter.delete(fileName);
                nbDeleted++;
            }
        }

        if (nbDeleted == 0) {
            log.info("No files to remove.");
        } else {
            log.info("Removed {} files from a total of {}.", nbDeleted, fileNames.size());
        }
    }
}
