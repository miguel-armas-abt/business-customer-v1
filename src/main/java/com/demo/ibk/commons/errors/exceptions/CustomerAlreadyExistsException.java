package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends GenericException {

  private static final String CODE = ExceptionCatalog.CUSTOMER_NOT_FOUND.getCode();
  private static final String MESSAGE = ExceptionCatalog.CUSTOMER_NOT_FOUND.getMessage();

  public CustomerAlreadyExistsException() {
    super(MESSAGE);
    this.httpStatus = HttpStatus.BAD_REQUEST;
    this.errorDetail = ErrorDto.builder()
      .code(CODE)
      .message(MESSAGE)
      .build();
  }
}
