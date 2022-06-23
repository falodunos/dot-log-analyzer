package com.dot.file.reader.service;

import com.dot.file.reader.config.AppConfig;
import com.dot.file.reader.persistence.model.UserAccess;
import com.dot.file.reader.persistence.repository.UserAccessRepository;
import com.dot.file.reader.web.controller.ApiController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.batch.core.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(TextFileProcessorService.class)
public class TextFileProcessorServiceTest {

    @MockBean
    Job job;

    @MockBean
    AppConfig appConfig;

    @MockBean
    UserAccessRepository userAccessRepository;

    @MockBean
    TextFileProcessorService textFileProcessorService;

    List<UserAccess> userAccessList = new ArrayList<>();

    @Before
    public void initialize() {
        UserAccess userAccess = new UserAccess();
        userAccess.setUpdatedAt(new Date());
        userAccess.setCreatedAt(new Date());
        userAccess.setDate(new Date().toString());
        userAccess.setId(Long.parseLong("1"));
        userAccess.setIp("");
        userAccess.setStatus("");
        userAccess.setRequest("");
        userAccessList.add(userAccess);
    }

    @Test
    public void hasUploadedRecordsTest() {
        when(userAccessRepository.findAll()).thenReturn(userAccessList);
        assert userAccessList.size() > 0;
    }

    @Test
    public void importTextFileToDatabaseTest() {
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(textFileProcessorService).importTextFileToDatabase();

    }
}
