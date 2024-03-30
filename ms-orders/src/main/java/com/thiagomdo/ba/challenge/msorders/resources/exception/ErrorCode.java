package com.thiagomdo.ba.challenge.msorders.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("Data not found"),

    BAD_REQUEST("Invalid data"),

    SYSTEM_ERROR("Unavailable server"),

    EMPTY_LIST("The list is empty");

    private final String message;
}
