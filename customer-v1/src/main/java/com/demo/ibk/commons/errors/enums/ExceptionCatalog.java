package com.demo.ibk.commons.errors.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCatalog {

    //technical
    INVALID_INPUT_FIELD("00.00.01", "Non-valid input field"),
    NO_SUCH_REST_CLIENT("00.00.02", "No such rest client service"),

    //business
    CUSTOMER_NOT_FOUND("01.01.01", "The customer doesn't exist"),
    INVALID_DOCUMENT_TYPE("01.01.02", "The document type is not defined"),
    CUSTOMER_ALREADY_EXISTS("01.01.03", "The customer already exists"),
    CRYPTOGRAPHY_RESPONSE_NULL("01.01.04", "Cryptography response is null");

    private final String code;
    private final String message;
}
