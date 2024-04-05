package com.thiagomdo.ba.challenge.msorders.service.exception;

import com.thiagomdo.ba.challenge.msorders.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ProductNotFoundException extends StandardCustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND.name());
        this.errorCode = ErrorCode.PRODUCT_NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }

}
