package com.thiagomdo.ba.challenge.msorders.resources.exception;

import com.thiagomdo.ba.challenge.msorders.service.exception.*;
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

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handlerProductNotFoundException(){
        var problem = new Problem(ErrorCode.PRODUCT_NOT_FOUND,HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    @ExceptionHandler(NotPossibleToChangeStatusException.class)
    public final ResponseEntity<Object> handlerNotPossibleToChangeStatusException(){
        var problem = new Problem(ErrorCode.NOT_POSSIBLE_CHANGE_STATUS, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    @ExceptionHandler(NotPossibleToChangeDateException.class)
    public final ResponseEntity<Object> handlerNotPossibleToChangeDateException(){
        var problem = new Problem(ErrorCode.NOT_POSSIBLE_CHANGE_DATE, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<Object> handlerFeignException(FeignException e){
        var problem = new Problem(ErrorCode.ERROR_WHEN_SEARCHING_FOR_FEIGN, HttpStatus.SERVICE_UNAVAILABLE);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problem);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handlerException(){
//        var problem = new Problem(ErrorCode.SYSTEM_ERROR);
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problem);
//    }
}
