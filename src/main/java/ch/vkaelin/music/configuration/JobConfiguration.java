package ch.vkaelin.music.configuration;

import ch.vkaelin.music.jobs.ClearOldSongFiles;
import lombok.RequiredArgsConstructor;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    private static final String EVERY_5_MINUTES = "*/5 * * * *";

    private final ApplicationContext applicationContext;

    @Bean
    public void initJobs() {
        JobRunr.configure()
                .useStorageProvider(new InMemoryStorageProvider())
                .useJobActivator(applicationContext::getBean)
                .useBackgroundJobServer()
                .useDashboard()
                .initialize();

        BackgroundJob.scheduleRecurrently(EVERY_5_MINUTES, ClearOldSongFiles::run);
    }
}
