package com.gidi.library.exception;

public class CurrentlyNotAvailableException extends RuntimeException {

    public CurrentlyNotAvailableException() {
    }

    public CurrentlyNotAvailableException(String message) {
        super(message);
    }

    public CurrentlyNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentlyNotAvailableException(Throwable cause) {
        super(cause);
    }
}
