package com.demo.ibk.commons.interceptor.error;

import static com.demo.ibk.commons.errors.enums.ExceptionCatalog.INVALID_INPUT_FIELD;
import static com.demo.ibk.commons.logging.utils.HeaderExtractor.extractTraceHeaders;

import com.demo.ibk.commons.errors.dto.ErrorDto;
import com.demo.ibk.commons.errors.exceptions.GenericException;
import com.demo.ibk.commons.logging.ThreadContextInjector;
import com.demo.ibk.commons.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorInterceptor extends ResponseEntityExceptionHandler {

  private final ApplicationProperties properties;

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ErrorDto> handleException(Throwable ex, WebRequest request) {
    generateTrace(ex, request);

    ErrorDto error = ErrorDto.getDefaultError(properties);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if(ex instanceof GenericException genericException) {
      error = genericException.getErrorDetail();
      httpStatus = genericException.getHttpStatus();
    }

    return new ResponseEntity<>(error, httpStatus);
  }

  //jakarta validations
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    generateTrace(ex, request);

    String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(";"));

    ErrorDto error = ErrorDto.builder()
        .code(INVALID_INPUT_FIELD.getCode())
        .message(errorMessage)
        .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private static void generateTrace(Throwable exception, WebRequest request) {
    String message = exception.getMessage();
    ThreadContextInjector.populateFromHeaders(extractTraceHeaders(request));

    log.error(message, exception);

    ThreadContext.clearAll();
  }
}
