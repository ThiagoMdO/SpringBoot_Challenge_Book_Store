package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handlerProductNotFoundException() {
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(EmptyListException.class)
    public final ResponseEntity<Object> handlerEmptyListException() {
        var problem = new Problem(ErrorCode.EMPTY_LIST, HttpStatus.OK);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public final ResponseEntity<Object> handlerProductAlreadyExistException(){
        var problem = new Problem(ErrorCode.CONFLICT_NAME, HttpStatus.CONFLICT);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(MinDescriptionException.class)
    public final ResponseEntity<Object> handlerMinDescriptionException(){
        var problem = new Problem(ErrorCode.MIM_SIZE_DESCRIPTION, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(MinValueException.class)
    public final ResponseEntity<Object> handlerMinValueException(){
        var problem = new Problem(ErrorCode.MIM_VALUE_PERMITTED, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(){
        var problem = new Problem(ErrorCode.SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problem);
    }
}
