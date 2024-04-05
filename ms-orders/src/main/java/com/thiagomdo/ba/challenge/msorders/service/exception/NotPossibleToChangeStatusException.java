package com.thiagomdo.ba.challenge.msorders.service.exception;

import com.thiagomdo.ba.challenge.msorders.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class NotPossibleToChangeStatusException extends StandardCustomException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public NotPossibleToChangeStatusException(){
        super(ErrorCode.NOT_POSSIBLE_CHANGE_STATUS.name());
        this.errorCode = ErrorCode.NOT_POSSIBLE_CHANGE_STATUS;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
