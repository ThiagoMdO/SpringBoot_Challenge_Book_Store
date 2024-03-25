package com.thiagomdo.ba.challenge.msproducts.services.exception;

import com.thiagomdo.ba.challenge.msproducts.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class MinValueException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public MinValueException(){
        super(ErrorCode.MIM_VALUE_PERMITTED.name());
        this.errorCode = ErrorCode.MIM_VALUE_PERMITTED;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
