package com.challenge.demo.exception;

public class InvalidRequestException extends Exception{
    public static final String INVALID_HOUR = "hours should be > 0 and <= 24";

    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidRequestException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
