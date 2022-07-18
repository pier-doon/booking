package com.ws.hotelsearch.exception.minio;

public class UnsupportedMediaTypeException extends RuntimeException {

    public UnsupportedMediaTypeException() {
        super();
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
