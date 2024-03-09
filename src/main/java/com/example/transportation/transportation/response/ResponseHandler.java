package com.example.transportation.transportation.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", httpStatus);

        return new ResponseEntity<>(map, httpStatus);
    }

    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", httpStatus);
        map.put("data", data);

        return new ResponseEntity<>(map, httpStatus);
    }
}
