package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import org.springframework.http.HttpStatus;

public class CryptographyResponseNullException extends GenericException {

    private static final ExceptionCatalog EXCEPTION = ExceptionCatalog.CRYPTOGRAPHY_RESPONSE_NULL;

    public CryptographyResponseNullException() {
        super(EXCEPTION.getMessage());
        this.httpStatus = HttpStatus.CONFLICT;
        this.errorDetail = ErrorDto.builder()
            .code(EXCEPTION.getCode())
            .message(EXCEPTION.getMessage())
            .build();
    }
}
