package com.thiagomdo.ba.challenge.msfeedback.resources.exception;

import com.thiagomdo.ba.challenge.msfeedback.services.exception.StandardCustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Problem {

    private final int code;

    private final String status;

    private final String message;

    public Problem(ErrorCode errorCode){
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.status = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public Problem(ErrorCode errorCode, HttpStatus httpStatus){
        this.code = httpStatus.value();
        this.status = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public <T extends StandardCustomException> Problem(ErrorCode errorCode, T exception){
        this.code = exception.getStatus().value();
        this.status = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
