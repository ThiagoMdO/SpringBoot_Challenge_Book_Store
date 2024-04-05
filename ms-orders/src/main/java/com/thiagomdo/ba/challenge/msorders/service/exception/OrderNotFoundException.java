package com.thiagomdo.ba.challenge.msorders.service.exception;

import com.thiagomdo.ba.challenge.msorders.resources.exception.ErrorCode;
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
        super(ErrorCode.NOT_FOUND.name());
        this.errorCode = ErrorCode.NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }

}
