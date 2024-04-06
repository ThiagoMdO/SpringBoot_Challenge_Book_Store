package com.thiagomdo.ba.challenge.msfeedback.services.exception;

import com.thiagomdo.ba.challenge.msfeedback.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class EmptyListException extends StandardCustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public EmptyListException(){
        super(ErrorCode.EMPTY_LIST.name());
        this.errorCode = ErrorCode.EMPTY_LIST;
        this.status = HttpStatus.OK;
    }



}
