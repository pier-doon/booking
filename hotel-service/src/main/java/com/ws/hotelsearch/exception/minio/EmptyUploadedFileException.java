package com.ws.hotelsearch.exception.minio;

public class EmptyUploadedFileException extends RuntimeException {

    public EmptyUploadedFileException(String message) {
        super(message);
    }

    public EmptyUploadedFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
