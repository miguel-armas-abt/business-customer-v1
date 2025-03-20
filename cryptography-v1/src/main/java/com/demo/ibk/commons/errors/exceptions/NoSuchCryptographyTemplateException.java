package com.demo.ibk.commons.errors.exceptions;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.enums.ExceptionCatalog;
import org.springframework.http.HttpStatus;

public class NoSuchCryptographyTemplateException extends GenericException {

  private static final ExceptionCatalog EXCEPTION = ExceptionCatalog.NO_SUCH_CRYPTOGRAPHY_TEMPLATE;

  public NoSuchCryptographyTemplateException() {
    super(EXCEPTION.getMessage());
    this.httpStatus = HttpStatus.BAD_REQUEST;
    this.errorDetail = ErrorDto.builder()
        .code(EXCEPTION.getCode())
        .message(EXCEPTION.getMessage())
        .build();
  }
}
