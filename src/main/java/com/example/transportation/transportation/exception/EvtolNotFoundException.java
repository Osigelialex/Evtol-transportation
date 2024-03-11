package com.example.transportation.transportation.exception;

public class EvtolNotFoundException extends RuntimeException {
    public EvtolNotFoundException() {
    }

    public EvtolNotFoundException(String message) {
        super(message);
    }
}
