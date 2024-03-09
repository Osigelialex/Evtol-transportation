package com.example.transportation.transportation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EvtolExceptionHandler {
    @ExceptionHandler(value = EvtolNotFoundException.class)
    public ResponseEntity<Object> handleEvtolNotFoundException(EvtolNotFoundException evtolNotFoundException) {
        EvtolException evtolException = new EvtolException(
                evtolNotFoundException.getMessage(),
                evtolNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(evtolException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EvtolDuplicateException.class)
    public ResponseEntity<Object> handleEvtolDuplicateException(EvtolDuplicateException evtolDuplicateException) {
        EvtolException evtolException = new EvtolException(
                evtolDuplicateException.getMessage(),
                evtolDuplicateException.getCause(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(evtolException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EvtolBadRequestException.class)
    public ResponseEntity<Object> handleEvtolBadRequestException(EvtolBadRequestException evtolBadRequestException) {
        EvtolException evtolException = new EvtolException(
                evtolBadRequestException.getMessage(),
                evtolBadRequestException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(evtolException, HttpStatus.BAD_REQUEST);
    }
}
