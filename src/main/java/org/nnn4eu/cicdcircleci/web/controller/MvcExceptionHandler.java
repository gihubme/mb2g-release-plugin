package org.nnn4eu.cicdcircleci.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<List> validationErrorHandling(ConstraintViolationException e) {

        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
        String errors = cause.getMessage();
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
