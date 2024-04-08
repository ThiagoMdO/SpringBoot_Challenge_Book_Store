package com.thiagomdo.ba.challenge.msfeedback.resources.exception;

import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(FeedBackNotFoundException.class)
    public ResponseEntity<Object> handlerFeedBackNotFoundException(){
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<Object> handlerEmptyListException(){
        var problem = new Problem(ErrorCode.EMPTY_LIST, HttpStatus.OK);
        return ResponseEntity.ok().body(problem);
    }

    @ExceptionHandler(NotPossibleToCommentOrderException.class)
    public ResponseEntity<Object> handlerNotPossibleToCommentOrderException(){
        var problem = new Problem(ErrorCode.NOT_POSSIBLE_TO_COMMENT_ORDER, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handlerOrderNotFoundException(){
        var problem = new Problem(ErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
