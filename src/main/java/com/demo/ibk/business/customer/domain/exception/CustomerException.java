package com.demo.ibk.business.customer.domain.exception;

import java.util.Optional;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
@Slf4j
@AllArgsConstructor
@Getter
public enum CustomerException {

  ERROR1000("/errors/no-data", "The customer does not exist", HttpStatus.BAD_REQUEST),
  ERROR1001("/errors/business-rules", "The document type is not defined", HttpStatus.BAD_REQUEST),

  ERROR1002("/errors/business-rules", "The customer code is already used currently", HttpStatus.BAD_REQUEST);

  private final String type;
  private final String description;
  private final HttpStatus status;

  public ApiException buildExceptionFromThrowable(Throwable cause) {
    return ApiException.builder(this.type, this.name(), this.description, this.status)
        .cause(cause)
        .build();
  }

  public ApiException buildCustomException() {
    return ApiException.builder(this.type, this.name(), this.description, this.status)
        .build();
  }

  public ApiException buildCustomException(String additionalDescription) {
    return ApiException.builder(this.type, this.name(),
        this.description + " - " +Optional.ofNullable(additionalDescription).orElse(Strings.EMPTY), this.status)
        .build();
  }
}
