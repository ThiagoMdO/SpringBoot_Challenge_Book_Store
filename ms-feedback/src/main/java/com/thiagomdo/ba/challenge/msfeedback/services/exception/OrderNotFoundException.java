package com.thiagomdo.ba.challenge.msfeedback.services.exception;

import com.thiagomdo.ba.challenge.msfeedback.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class OrderNotFoundException extends StandardCustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public OrderNotFoundException(){
        super(ErrorCode.ORDER_NOT_FOUND.name());
        this.errorCode = ErrorCode.ORDER_NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }

}
