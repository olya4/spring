package com.geekbrains.market.winter7.cart.exceptions;

import com.geekbrains.market.winter7.api.AppError;
import com.geekbrains.market.winter7.api.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // перехватить исключение
    @ExceptionHandler
    // если это исключение типа ResourceNotFoundException или его наследники
    // поймать и упаковать в AppError
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        // залогировать ошибку, чтоб она была выведена в консоль, а не только клиенту
        log.error(resourceNotFoundException.getMessage(), resourceNotFoundException);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }
}
