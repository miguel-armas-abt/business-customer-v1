package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import org.springframework.http.HttpStatus;

import static com.demo.ibk.commons.errors.enums.ExceptionCatalog.RSA_UNEXPECTED_PUBLIC_KEY_READING;

public class RsaUnexpectedPublicKeyReadingException extends GenericException {

    private static final ExceptionCatalog EXCEPTION = RSA_UNEXPECTED_PUBLIC_KEY_READING;

    public RsaUnexpectedPublicKeyReadingException(Throwable ex) {
        super(ex.getMessage());
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDetail = ErrorDto.builder()
            .code(EXCEPTION.getCode())
            .message(EXCEPTION.getMessage())
            .build();
    }
}
