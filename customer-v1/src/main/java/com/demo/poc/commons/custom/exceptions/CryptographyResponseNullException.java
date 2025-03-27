package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.enums.ErrorDictionary;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import org.springframework.http.HttpStatus;

public class CryptographyResponseNullException extends GenericException {

    private static final ErrorDictionary EXCEPTION = ErrorDictionary.CRYPTOGRAPHY_RESPONSE_NULL;

    public CryptographyResponseNullException() {
        super(EXCEPTION.getMessage());
        this.httpStatus = HttpStatus.CONFLICT;
        this.errorDetail = ErrorDto.builder()
            .code(EXCEPTION.getCode())
            .message(EXCEPTION.getMessage())
            .build();
    }
}
