package com.dataimport.api;

import java.io.IOException;
import java.io.InputStream;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class UploadFileHelper {
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static InputStream isValidFile(MultipartFile file) {

        if (file == null || file.getOriginalFilename() == null || file.isEmpty()) {
            throw new ReadFileException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals(CONTENT_TYPE)) {
            throw new ReadFileException("File is not an excel file");
        }

        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new ReadFileException(e.getMessage());
        }

    }

}
