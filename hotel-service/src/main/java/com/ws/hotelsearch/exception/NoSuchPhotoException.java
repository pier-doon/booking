package com.ws.hotelsearch.exception;

public class NoSuchPhotoException extends RuntimeException {
    public NoSuchPhotoException() {
        super();
    }

    public NoSuchPhotoException(String message) {
        super(message);
    }

    public NoSuchPhotoException(String message, Throwable cause) {
        super(message, cause);
    }
}
