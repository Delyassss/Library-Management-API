package com.delyassss.demo;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.logging.FileHandler;

@RestControllerAdvice
public class GlobalExeptionHandler
{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> HandleFailedInput(MethodArgumentNotValidException ex)
    {
        Map<String, List<String>> res = new HashMap<>();
        List<FieldError> fieldError = ex.getFieldErrors();

        Iterator<FieldError> iterator = fieldError.iterator();

        while (iterator.hasNext())
        {

            FieldError error = iterator.next();
            res.computeIfAbsent(error.getField() , initializeWith -> new ArrayList<>()).add(error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> HandleFailedInput(ConstraintViolationException ex)
    {
        Map<String, List<String>> res = new HashMap<>();

        ex.getConstraintViolations().forEach(error -> {
            String msg = error.getMessage();
            String field = error.getPropertyPath().toString();
            res.computeIfAbsent(field, initializeWith -> new ArrayList<>()).add(msg);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }


    @ExceptionHandler(TaskNotFoundExeption.class)
    public ResponseEntity<String>  HandleNotFound(TaskNotFoundExeption exeption)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exeption.getMessage());
    }

}
