package ru.geekbrains.homework_7.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // перехватить исключение
    @ExceptionHandler
    // если это исключение типа ResourceNotFoundException или его наследники
    // поймать и упаковать в AppError
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }
}
