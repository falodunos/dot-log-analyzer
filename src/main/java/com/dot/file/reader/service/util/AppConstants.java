package com.dot.file.reader.service.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppConstants {

    public static final String APP_CONTEXT = "/dot/logger/v1/";

    public static final String FILE_TYPE = "text/csv";
    public static final String FILE_UPLOAD_PATH = getFileUploadPath();
    public static final String TARGET_FILE_NAME = "users_access.txt";
    public static final String TARGET_FILE_PATH = getFileUploadPath() + TARGET_FILE_NAME;
    public static final String INTEGER_REGEX_PATTERN = "[+-]?[0-9]+";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


    /**
     * Return the path of uploaded files
     * @return String
     */
    private static String getFileUploadPath() {
        String path = System.getProperty("user.dir") + "\\uploads\\";
        path = path.replace("\\", "/");
        return path;
    }

    public interface ApiResponseMessage {
        String SUCCESSFUL = "Successful";
        String FAILED = "Failed";
    }
}
