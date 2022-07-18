package com.ws.hotelsearch.exception.handler;

import com.ws.hotelsearch.exception.minio.EmptyUploadedFileException;
import com.ws.hotelsearch.exception.minio.UnsupportedMediaTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MinioHandler {

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<?> unsupportedMediaTypeException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyUploadedFileException.class)
    public ResponseEntity<?> emptyFileException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
