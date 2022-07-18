package com.ws.hotelsearch.exception.handler;

import com.ws.hotelsearch.exception.EmptyPhotoRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestReqExceptionHandler {

    @ExceptionHandler({EmptyPhotoRequestException.class})
    public ResponseEntity<?> nullRequestBodyHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
