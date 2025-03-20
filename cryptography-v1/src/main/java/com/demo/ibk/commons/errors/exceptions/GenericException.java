package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    protected ErrorDto errorDetail;
    protected HttpStatus httpStatus;

    public GenericException(String message) {
        super(message);
    }
}
