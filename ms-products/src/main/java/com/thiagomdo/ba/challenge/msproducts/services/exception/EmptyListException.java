package com.thiagomdo.ba.challenge.msproducts.services.exception;

import com.thiagomdo.ba.challenge.msproducts.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class EmptyListException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private HttpStatus status;

    public EmptyListException(){
        super(ErrorCode.EMPTY_LIST.name());
        this.errorCode = ErrorCode.EMPTY_LIST;
        this.status = HttpStatus.OK;
    }

}
