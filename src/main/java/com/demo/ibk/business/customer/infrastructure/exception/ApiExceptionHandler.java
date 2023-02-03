package com.demo.ibk.business.customer.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiException;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiExceptionDetail;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse
        .builder("/errors/bad-requests", ex.getCause().getMessage(), "BAD_REQUEST_BODY_STRUCTURE")
        .build();
    return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> errorMessageList = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    List<ApiExceptionDetail> detailList = new ArrayList<>();
    for (String errorMessage: errorMessageList) {
      detailList.add(ApiExceptionDetail.builder()
          .title(errorMessage)
          .component("business-menu-option")
          .build());
    }

    ApiExceptionResponse exceptionResponse = ApiExceptionResponse
        .builder("/errors/bad-requests", "Invalid request body", "BAD_REQUEST_BODY")
        .details(detailList)
        .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApiException.class)
  public final ResponseEntity<ApiExceptionResponse> sendException(ApiException ex, WebRequest request) {
    return new ResponseEntity<>(ApiExceptionResponse.builder(ex.getType(), ex.getTitle(), ex.getErrorCode()).build(),
        ex.getStatus());
  }

}
