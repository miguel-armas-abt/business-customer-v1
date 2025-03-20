package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoSuchRestClientException extends GenericException {

    private static final ExceptionCatalog EXCEPTION = ExceptionCatalog.NO_SUCH_REST_CLIENT;

    public NoSuchRestClientException() {
        super(EXCEPTION.getMessage());
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDetail = ErrorDto.builder()
            .code(EXCEPTION.getCode())
            .message(EXCEPTION.getMessage())
            .build();
    }
}
