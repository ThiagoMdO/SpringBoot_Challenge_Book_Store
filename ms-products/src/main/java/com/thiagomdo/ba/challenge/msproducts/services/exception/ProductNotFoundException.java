package com.thiagomdo.ba.challenge.msproducts.services.exception;

import com.thiagomdo.ba.challenge.msproducts.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ProductNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public ProductNotFoundException(){
        super(ErrorCode.NOT_FOUND.name());
        this.errorCode = ErrorCode.NOT_FOUND;
        this.status =HttpStatus.NOT_FOUND;
    }

}
