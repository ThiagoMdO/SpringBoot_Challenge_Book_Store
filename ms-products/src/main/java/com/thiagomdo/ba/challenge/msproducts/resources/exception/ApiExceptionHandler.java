package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import com.thiagomdo.ba.challenge.msproducts.services.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        var problem = new Problem(exception.getErrorCode(), exception);
        return ResponseEntity.status(problem.getCode()).body(problem);
    }

}
