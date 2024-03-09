package com.example.transportation.transportation.exception;

public class EvtolDuplicateException extends RuntimeException {
    public EvtolDuplicateException() {
    }

    public EvtolDuplicateException(String message) {
        super(message);
    }

    public EvtolDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
