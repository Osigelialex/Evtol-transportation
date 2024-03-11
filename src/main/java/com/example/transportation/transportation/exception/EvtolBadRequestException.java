package com.example.transportation.transportation.exception;

public class EvtolBadRequestException extends RuntimeException {
    public EvtolBadRequestException() {
    }

    public EvtolBadRequestException(String message) {
        super(message);
    }
}
