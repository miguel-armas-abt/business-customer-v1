package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidDocumentTypeException extends GenericException {

    private static final String CODE = ExceptionCatalog.INVALID_DOCUMENT_TYPE.getCode();
    private static final String MESSAGE = ExceptionCatalog.INVALID_DOCUMENT_TYPE.getMessage();

    public InvalidDocumentTypeException() {
        super(MESSAGE);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorDetail = ErrorDto.builder()
            .code(CODE)
            .message(MESSAGE)
            .build();
    }
}
