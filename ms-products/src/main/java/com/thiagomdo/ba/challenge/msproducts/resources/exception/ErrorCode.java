package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND("Data not found"),

    BAD_REQUEST("Invalid data"),

    CONFLICT("Conflict between the fields"),

    SYSTEM_ERROR("Unavailable server"),

    EMPTY_LIST("The list is empty");

    private final String message;
}
