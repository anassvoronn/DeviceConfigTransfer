package org.nastya.exception;

public class ConsumerProcessingException extends RuntimeException {

    public ConsumerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}