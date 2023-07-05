package com.example.asianfoodapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {

         List<String> errors = ex
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(x -> x.getField() + " - " + x.getDefaultMessage())
                        .collect(Collectors.toList());
        return handleError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity handleWebClientException(WebClientResponseException ex){
        return handleError(HttpStatus.BAD_REQUEST, List.of(ex.getResponseBodyAsString()));
    }

    private static ResponseEntity<Object> handleError(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", new Date());
        body.put("status", status);
        body.put("errors", errors);
        return new ResponseEntity<>(body, status);
    }
}
