package com.qcaldwell.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class, IllegalArgumentException.class, NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequestExceptions(final Exception exception, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException exception, WebRequest request) {
        StringBuilder invalidFields = new StringBuilder("{");
        exception.getBindingResult().getAllErrors().forEach(error -> invalidFields.append(((FieldError) error).getField()).append("=").append(error.getDefaultMessage()).append(", "));
        invalidFields.delete(invalidFields.length() - 2, invalidFields.length()).append("}");
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                invalidFields.toString(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ErrorMessage userNotFoundException(UserNotFoundException userNotFoundExeption){
        return new ErrorMessage(1, userNotFoundExeption.getMessage(), userNotFoundExeption.getMessage());
    }
}
