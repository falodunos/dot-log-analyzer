package com.dot.file.reader.service;

import com.dot.file.reader.exception.FileStorageException;
import com.dot.file.reader.service.util.AppConstants;
import com.dot.file.reader.web.dto.response.BaseResponse;
import com.dot.file.reader.config.AppConfig;
import com.dot.file.reader.web.dto.response.UploadFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
@Slf4j
public class FileStorageService {


    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(AppConfig appConfig) {
        this.fileStorageLocation = Paths.get(AppConstants.FILE_UPLOAD_PATH)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Upload file to local directory
     * @param file
     * @return String
     */
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            this.renameFile(fileName); // always rename uploaded file to user_access.txt to ensure easy accessibility

            return AppConstants.TARGET_FILE_NAME;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * Rename uploaded file name to user_access.txt
     * @param fileName
     * @throws IOException
     * @return void
     */
    private void renameFile(String fileName) throws IOException {
        File fileToMove = new File(AppConstants.FILE_UPLOAD_PATH + fileName);
        boolean isMoved = fileToMove.renameTo(new File(AppConstants.TARGET_FILE_PATH));
        if (!isMoved) {
            throw new FileSystemException(AppConstants.TARGET_FILE_PATH);
        }
    }

    /**
     * @param file
     * @return boolean
     */
    public boolean hasPlainTextFormat(MultipartFile file) {
        if (!AppConstants.FILE_TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**
     * Perform file upload to a local folder or directory
     * @param file
     * @return
     */
    public BaseResponse uploadFile(MultipartFile file) {
        UploadFileResponse uploadFileResponse = null;
        BaseResponse response;

        if (hasPlainTextFormat(file)) {
            try {
                String fileName = storeFile(file); // upload file and return uploaded file name

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileName)
                        .toUriString();
                // aggregate response for the upload file
                uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
            } catch (Exception ex) {
                ex.printStackTrace();
                return new BaseResponse(HttpStatus.EXPECTATION_FAILED.value() + "", ex.getMessage());
            }
        }

        response = uploadFileResponse != null ?
                new BaseResponse(HttpStatus.OK.value() + "", AppConstants.ApiResponseMessage.SUCCESSFUL, uploadFileResponse) :
                new BaseResponse(HttpStatus.EXPECTATION_FAILED.value() + "", AppConstants.ApiResponseMessage.FAILED);

        log.info("Text File Upload Response : " + response.toString());
        return response;
    }
}