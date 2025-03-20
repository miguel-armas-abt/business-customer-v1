package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends GenericException {

  private static final ExceptionCatalog EXCEPTION = ExceptionCatalog.CUSTOMER_ALREADY_EXISTS;

  public CustomerAlreadyExistsException() {
    super(EXCEPTION.getMessage());
    this.httpStatus = HttpStatus.BAD_REQUEST;
    this.errorDetail = ErrorDto.builder()
      .code(EXCEPTION.getCode())
      .message(EXCEPTION.getMessage())
      .build();
  }
}
