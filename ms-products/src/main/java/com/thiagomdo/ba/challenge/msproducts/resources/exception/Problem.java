package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiagomdo.ba.challenge.msproducts.services.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Problem {

    @JsonProperty("Code")
    private final int code;

    @JsonProperty("Status")
    private final String status;

    @JsonProperty("Message")
    private final String message;


    public Problem(ErrorCode errorCode, ProductNotFoundException exception){
        this.code =exception.getStatus().value();
        this.status = errorCode.name();
        this.message = errorCode.getMessage();
    }

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
}
