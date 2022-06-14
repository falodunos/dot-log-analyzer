package com.dot.file.reader.service.listener;

import com.dot.file.reader.persistence.repository.UserAccessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JobCompletionListener extends JobExecutionListenerSupport {

    @Autowired
    UserAccessRepository userAccessRepository;

    // The callback method from the Spring Batch JobExecutionListenerSupport class that is executed when the batch process is completed
    @Override
    public void afterJob(JobExecution jobExecution) {
        // When the batch process is completed the users in the database are retrieved and logged on the application logs
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB COMPLETED !!!");
            log.info(userAccessRepository.findAll().size() + "records uploaded to database!");
        }
    }
}
