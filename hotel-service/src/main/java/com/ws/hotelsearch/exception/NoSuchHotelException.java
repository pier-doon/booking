package com.ws.hotelsearch.exception;

public class NoSuchHotelException extends RuntimeException{
    public NoSuchHotelException() {
        super();
    }

    public NoSuchHotelException(String message) {
        super(message);
    }

    public NoSuchHotelException(String message, Throwable cause) {
        super(message, cause);
    }
}
