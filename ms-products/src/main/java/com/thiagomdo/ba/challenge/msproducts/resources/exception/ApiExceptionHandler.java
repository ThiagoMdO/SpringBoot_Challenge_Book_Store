package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handlerProductNotFoundException(ProductNotFoundException exception) {
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(EmptyListException.class)
    public final ResponseEntity<Object> handlerEmptyListException(EmptyListException exception) {
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public final ResponseEntity<Object> handlerProductAlreadyExistException(ProductAlreadyExistException exception){
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(MinDescriptionException.class)
    public final ResponseEntity<Object> handlerProductAlreadyExistException(MinDescriptionException exception){
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

    @ExceptionHandler(MinValueException.class)
    public final ResponseEntity<Object> handlerProductAlreadyExistException(MinValueException exception){
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }
}
