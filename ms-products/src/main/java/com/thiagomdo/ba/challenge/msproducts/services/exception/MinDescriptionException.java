package com.thiagomdo.ba.challenge.msproducts.services.exception;

import com.thiagomdo.ba.challenge.msproducts.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class MinDescriptionException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public MinDescriptionException(){
        super(ErrorCode.MIM_SIZE_DESCRIPTION.name());
        this.errorCode = ErrorCode.MIM_SIZE_DESCRIPTION;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
