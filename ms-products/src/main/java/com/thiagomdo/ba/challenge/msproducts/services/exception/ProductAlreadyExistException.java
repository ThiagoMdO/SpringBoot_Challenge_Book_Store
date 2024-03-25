package com.thiagomdo.ba.challenge.msproducts.services.exception;

import com.thiagomdo.ba.challenge.msproducts.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ProductAlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public ProductAlreadyExistException(){
        super(ErrorCode.CONFLICT_NAME.name());
        this.errorCode = ErrorCode.CONFLICT_NAME;
        this.status = HttpStatus.CONFLICT;
    }
}
