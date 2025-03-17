package com.demo.ibk.commons.errors.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCatalog {

    CUSTOMER_NOT_FOUND("01.01.01", "The customer doesn't exist"),
    INVALID_DOCUMENT_TYPE("01.01.02", "The document type is not defined");

    private final String code;
    private final String message;
}
