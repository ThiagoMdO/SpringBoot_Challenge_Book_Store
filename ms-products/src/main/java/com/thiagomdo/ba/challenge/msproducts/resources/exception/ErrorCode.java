package com.thiagomdo.ba.challenge.msproducts.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND("Data not found"),

    BAD_REQUEST("Invalid data"),

    MIM_SIZE_DESCRIPTION("The field must have at least 10 characters"),

    MIM_VALUE_PERMITTED("It's not possible a negative value"),

    CONFLICT("Conflict between the fields"),

    CONFLICT_NAME("The name is already in use"),

    SYSTEM_ERROR("Unavailable server"),

    EMPTY_LIST("The list is empty");

    private final String message;
}
