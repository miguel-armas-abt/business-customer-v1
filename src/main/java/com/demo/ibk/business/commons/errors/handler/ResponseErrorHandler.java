package com.demo.ibk.business.commons.errors.handler;

import com.demo.ibk.business.commons.errors.dto.ErrorDto;
import com.demo.ibk.business.commons.errors.exceptions.GenericException;
import com.demo.ibk.business.commons.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

  private final ApplicationProperties properties;

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ErrorDto> handleException(Throwable ex, WebRequest request) {
    ErrorDto error = ErrorDto.getDefaultError(properties);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if(ex instanceof GenericException genericException) {
      error = genericException.getErrorDetail();
      httpStatus = genericException.getHttpStatus();
    }

    return new ResponseEntity<>(error, httpStatus);
  }
}
