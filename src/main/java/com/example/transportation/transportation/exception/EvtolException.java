package com.example.transportation.transportation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvtolException {

    private String message;
    private HttpStatus httpStatus;
}
