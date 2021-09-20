package ru.otus.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final Job importUserJob;

    private final JobLauncher jobLauncher;

    private final JobExplorer jobExplorer;

    private final JobOperator jobOperator;

    public void startMigration() throws Exception {
        jobLauncher.run(importUserJob, new JobParametersBuilder().toJobParameters());
    }

    public void restartJob(long id) throws Exception {
        final var restartId = jobOperator.restart(id);
        jobExplorer.getJobExecution(restartId);
    }
}
