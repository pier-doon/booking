package com.ws.hotelsearch.exception;

public class EmptyPhotoRequestException extends RuntimeException {
    public EmptyPhotoRequestException() {
        super();
    }

    public EmptyPhotoRequestException(String message) {
        super(message);
    }

    public EmptyPhotoRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
