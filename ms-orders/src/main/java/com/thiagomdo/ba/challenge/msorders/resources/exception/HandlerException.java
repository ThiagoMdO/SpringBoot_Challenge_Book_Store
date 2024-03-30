package com.thiagomdo.ba.challenge.msorders.resources.exception;

import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(EmptyListException.class)
    public final ResponseEntity<Object> handlerEmptyListException(){
        var problem = new Problem(ErrorCode.EMPTY_LIST, HttpStatus.OK);
        return ResponseEntity.ok().body(problem);
    }
}
