package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.enums.ErrorDictionary;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import org.springframework.http.HttpStatus;

import static com.demo.poc.commons.core.errors.enums.ErrorDictionary.RSA_UNEXPECTED_PRIVATE_KEY_READING;

public class RsaUnexpectedPrivateKeyReadingException extends GenericException {

    private static final ErrorDictionary EXCEPTION = RSA_UNEXPECTED_PRIVATE_KEY_READING;

    public RsaUnexpectedPrivateKeyReadingException(Throwable ex) {
        super(ex.getMessage());
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDetail = ErrorDto.builder()
            .code(EXCEPTION.getCode())
            .message(EXCEPTION.getMessage())
            .build();
    }
}
