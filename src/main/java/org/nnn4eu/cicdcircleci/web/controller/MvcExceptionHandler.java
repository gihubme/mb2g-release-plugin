package org.nnn4eu.cicdcircleci.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@EnableWebMvc
@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> pageNotFoundExceptionHandling(NoHandlerFoundException e) {
        String errors = e.getMessage();
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionHandling(EntityNotFoundException e) {
        String errors = e.getMessage();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorResult> validationErrorHandling(ConstraintViolationException e) {
        ErrorResult errorResult = new ErrorResult();
        e.getConstraintViolations().forEach(constraintViolation -> {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getMessage()));
        });
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindException(BindException e) {
        ErrorResult errorResult = new ErrorResult();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(fieldError.getField(),
                            fieldError.getDefaultMessage()));
        }
        return errorResult;
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<String> httpMessageNotReadableErrorHandling(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        String errors = (cause != null) ? cause.getMessage() : e.getMessage();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

  /*  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List> validationErrorHandling(MethodArgumentNotValidException nve) {
        List<String> errors = new ArrayList<>(nve.getBindingResult().getAllErrors().size());
        nve.getBindingResult().getAllErrors().forEach(objectErr -> {
            errors.add(objectErr.getCodes()[0] + " : " + objectErr.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(fieldError.getField(),
                            fieldError.getDefaultMessage()));
        }
        return errorResult;
    }


}
