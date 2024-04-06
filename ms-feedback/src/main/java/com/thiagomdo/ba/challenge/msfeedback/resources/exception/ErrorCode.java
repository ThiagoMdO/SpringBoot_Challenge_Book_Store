package com.thiagomdo.ba.challenge.msfeedback.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("Data not found"),

    BAD_REQUEST("Invalid data"),

    SYSTEM_ERROR("Unavailable server"),

    EMPTY_LIST("The list is empty"),

    ERROR_WHEN_SEARCHING_FOR_FEIGN("Error in searching, connection with :FEIGN: doesn't exist");

    private final String message;
}
