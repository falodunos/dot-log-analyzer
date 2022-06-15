package com.dot.file.reader.controller;

import com.dot.file.reader.service.FileStorageService;
import com.dot.file.reader.service.util.AppConstants;
import com.dot.file.reader.web.controller.ApiController;
import com.dot.file.reader.web.dto.response.BaseResponse;
import com.dot.file.reader.web.dto.response.UploadFileResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FileStorageService fileStorageService;

    MockMultipartFile file = new MockMultipartFile(
            "file", "user_access.txt",
            MediaType.TEXT_PLAIN_VALUE, "".getBytes());

    UploadFileResponse uploadFileResponse = new UploadFileResponse("user_access.txt", "http://localhost:8084/downloadFile/user_access.txt", "text/plain", 17);
    BaseResponse response = new BaseResponse(HttpStatus.OK.value() + "", AppConstants.ApiResponseMessage.SUCCESSFUL, uploadFileResponse);


    @Test
    public void uploadFileTest() throws Exception {
        when(fileStorageService.uploadFile(file)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .multipart("/dot/logger/v1/upload")
                .file(file)
                .accept(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(response.toString()))
                .andReturn();
    }

}
