package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomerNotFoundException extends GenericException {

    private static final String CODE = ExceptionCatalog.CUSTOMER_NOT_FOUND.getCode();
    private static final String MESSAGE = ExceptionCatalog.CUSTOMER_NOT_FOUND.getMessage();

    public CustomerNotFoundException() {
        super(MESSAGE);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDetail = ErrorDto.builder()
            .code(CODE)
            .message(MESSAGE)
            .build();
    }
}
