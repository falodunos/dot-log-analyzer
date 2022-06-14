package com.dot.file.reader.service;

import com.dot.file.reader.persistence.repository.UserAccessRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextFileProcessorService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    UserAccessRepository userAccessRepository;

    public void clearTable() {
        userAccessRepository.deleteAll();
    }

    public boolean hasUploadedRecords() {
        return this.userAccessRepository.findAll().size() > 0;
    }

    public void importTextFileToDatabase() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }
}
