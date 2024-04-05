package com.thiagomdo.ba.challenge.msorders.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("Data not found"),

    BAD_REQUEST("Invalid data"),

    SYSTEM_ERROR("Unavailable server"),

    ADDRESS_INCORRECT("Incorrect format or data, check fields address"),

    PRODUCT_NOT_FOUND("Id product is incorrect"),

    NOT_POSSIBLE_CHANGE_STATUS("It's not possible to change the Status Order"),

    NOT_POSSIBLE_CHANGE_DATE("It's not possible to change the order, because it has been at last 90 days since the order was issued"),

    EMPTY_LIST("The list is empty"),

    ERROR_WHEN_SEARCHING_FOR_FEIGN("Error in searching, connection with :FEIGN: doesn't exist");

    private final String message;
}
