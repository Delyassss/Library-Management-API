package com.delyassss.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> HandleFailedInput(MethodArgumentNotValidException ex)
    {
        return null;
    }

    @ExceptionHandler(TaskNotFoundExeption.class)
    public ResponseEntity<String>  HandleNotFound(TaskNotFoundExeption exeption)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exeption.getMessage());
    }

}
