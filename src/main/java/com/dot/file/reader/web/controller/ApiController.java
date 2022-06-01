package com.dot.file.reader.web.controller;

import com.dot.file.reader.service.FileStorageService;
import com.dot.file.reader.service.util.AppConstants;
import com.dot.file.reader.web.dto.response.BaseResponse;
import com.dot.file.reader.web.dto.response.UploadFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping(value = AppConstants.APP_CONTEXT)
@Slf4j
public class ApiController {

    @Autowired
    FileStorageService fileStorageService;

    /**
     * Print the matrix representation of the supplied CSV file
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String echo(@RequestParam("file") MultipartFile file) throws IOException {

        BaseResponse uploadFileResponse = fileStorageService.uploadFile(file);

        return uploadFileResponse.toString();
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<BaseResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

        return ResponseEntity.ok().body(new BaseResponse(HttpStatus.OK.toString(), "Successful", uploadFileResponse));
    }
}
