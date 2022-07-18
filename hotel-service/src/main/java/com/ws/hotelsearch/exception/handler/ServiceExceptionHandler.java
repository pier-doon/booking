package com.ws.hotelsearch.exception.handler;

import com.ws.hotelsearch.exception.EmptyPhotoRequestException;
import com.ws.hotelsearch.exception.NoSuchHotelException;
import com.ws.hotelsearch.exception.NoSuchPhotoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler({NoSuchHotelException.class, NoSuchPhotoException.class})
    public ResponseEntity<?> nullRequestBodyHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmptyPhotoRequestException.class})
    public ResponseEntity<?> emptyPhotoRequestException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
