package com.dataimport.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReadFileException extends RuntimeException {
    public ReadFileException(String message) {
        super("Error reading file import: " + message);
    }
}
