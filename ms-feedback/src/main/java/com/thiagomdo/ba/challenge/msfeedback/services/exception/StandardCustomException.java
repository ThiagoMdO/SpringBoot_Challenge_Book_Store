package com.thiagomdo.ba.challenge.msfeedback.services.exception;

import com.thiagomdo.ba.challenge.msfeedback.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class StandardCustomException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private HttpStatus status;

    public StandardCustomException(String msn){
        super(msn);
        this.errorCode = ErrorCode.SYSTEM_ERROR;
        this.status = HttpStatus.SERVICE_UNAVAILABLE;
    }
}
