package com.thiagomdo.ba.challenge.msfeedback.services.exception;

import com.thiagomdo.ba.challenge.msfeedback.resources.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class NotPossibleToCommentOrderException extends StandardCustomException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public NotPossibleToCommentOrderException(){
        super(ErrorCode.NOT_POSSIBLE_TO_COMMENT_ORDER.name());
        this.errorCode = ErrorCode.NOT_POSSIBLE_TO_COMMENT_ORDER;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
