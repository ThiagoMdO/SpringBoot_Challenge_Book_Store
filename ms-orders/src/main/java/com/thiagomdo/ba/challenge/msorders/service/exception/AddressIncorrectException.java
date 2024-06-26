package com.thiagomdo.ba.challenge.msorders.service.exception;

import com.thiagomdo.ba.challenge.msorders.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class AddressIncorrectException extends StandardCustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public AddressIncorrectException(){
        super(ErrorCode.ADDRESS_INCORRECT.name());
        this.errorCode = ErrorCode.ADDRESS_INCORRECT;
        this.status = HttpStatus.BAD_REQUEST;
    }

}
