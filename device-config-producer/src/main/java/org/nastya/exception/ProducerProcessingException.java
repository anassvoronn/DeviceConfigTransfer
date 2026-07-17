package org.nastya.exception;

public class ProducerProcessingException extends RuntimeException {

    public ProducerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}