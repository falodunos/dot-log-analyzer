package com.dot.file.reader.web.controller;

import com.dot.file.reader.service.FileStorageService;
import com.dot.file.reader.service.RunnableObject;
import com.dot.file.reader.service.TextFileProcessorService;
import com.dot.file.reader.service.util.AppConstants;
import com.dot.file.reader.web.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(value = AppConstants.APP_CONTEXT)
@Slf4j
public class ApiController {

    @Autowired
    FileStorageService fileStorageService;

//    @Autowired
//    TextFileProcessorService textFileProcessorService;

    /**
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("file :: " + file.toString());
        BaseResponse uploadFileResponse = fileStorageService.uploadFile(file);

        if (uploadFileResponse.getStatus().equals("200")) {
            // upload file to database on a different thread
//            Executor executor = Executors.newSingleThreadExecutor();
//            RunnableObject runnable = new RunnableObject(textFileProcessorService);
//            executor.execute(runnable);
        }

        return uploadFileResponse.toString();
    }
}
