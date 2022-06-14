package com.dot.file.reader.config.spring;

import com.dot.file.reader.persistence.model.UserAccess;
import com.dot.file.reader.persistence.repository.UserAccessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import com.dot.file.reader.service.util.AppConstants;


@Slf4j
@Configuration
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Bean
    public FlatFileItemReader<UserAccess> reader() {
        FlatFileItemReader<UserAccess> itemReader = null;

        FileSystemResource resource = new FileSystemResource(AppConstants.TARGET_FILE_PATH);

        itemReader = new FlatFileItemReader<>();
        itemReader.setResource(resource);
        itemReader.setName("textFileReader");
//        itemReader.setLinesToSkip(1); // Skip first line where it is necessary e.g. for csv file
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<UserAccess> lineMapper() {
        DefaultLineMapper<UserAccess> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("date", "ip", "request", "status", "userAgent");
        BeanWrapperFieldSetMapper<UserAccess> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserAccess.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);

        return lineMapper;
    }

    @Bean
    UserAccessLogProcessor processor() {
        UserAccessLogProcessor userAccessLogProcessor = new UserAccessLogProcessor();
        return userAccessLogProcessor;
    }

    @Bean
    RepositoryItemWriter<UserAccess> writer() {
        RepositoryItemWriter<UserAccess> writer = new RepositoryItemWriter<>();

        writer.setRepository(userAccessRepository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("text-step").<UserAccess, UserAccess>chunk(50)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                //.taskExecutor(taskExecutor()) // execute task in thread to improve performance
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("importUserAccessLog")
                .flow(step1()).end().build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

//    @Bean
//    public BeanValidatingItemProcessor<UserAccess> beanValidatingItemProcessor() throws Exception {
//        BeanValidatingItemProcessor<UserAccess> beanValidatingItemProcessor = new BeanValidatingItemProcessor<>();
//        beanValidatingItemProcessor.setFilter(true);
//
//        return beanValidatingItemProcessor;
//    }
}
