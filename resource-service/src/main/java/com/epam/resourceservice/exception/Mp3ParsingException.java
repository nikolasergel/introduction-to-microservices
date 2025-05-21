package com.epam.resourceservice.exception;

public class Mp3ParsingException extends RuntimeException{
    public Mp3ParsingException() {
    }

    public Mp3ParsingException(String message) {
        super(message);
    }

    public Mp3ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public Mp3ParsingException(Throwable cause) {
        super(cause);
    }

    public Mp3ParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
