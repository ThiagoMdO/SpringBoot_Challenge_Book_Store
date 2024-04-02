package com.thiagomdo.ba.challenge.msorders.resources.exception;

import com.thiagomdo.ba.challenge.msorders.service.exception.AddressIncorrectException;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msorders.service.exception.OrderNotFoundException;
import feign.FeignException;
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

    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<Object> handlerOrderNotFoundException(){
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    @ExceptionHandler(AddressIncorrectException.class)
    public final ResponseEntity<Object> handlerAddressIncorrectException(){
        var problem = new Problem(ErrorCode.ADDRESS_INCORRECT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<Object> handlerFeignException(){
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
