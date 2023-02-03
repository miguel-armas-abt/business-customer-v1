package com.demo.ibk.business.customer.infrastructure.exception.builder;

import com.demo.ibk.business.customer.infrastructure.exception.model.ApiException;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiExceptionDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApiExceptionBuilder {

  private String type;
  private String errorCode;
  private String title;
  private final List<ApiExceptionDetail> details = new ArrayList<>();
  private Throwable cause;
  private HttpStatus status;

  public ApiExceptionBuilder type(String type) {
    Optional.ofNullable(type)
        .filter(StringUtils::isNotBlank)
        .ifPresent(actualType -> this.type = actualType);
    return this;
  }

  public ApiExceptionBuilder errorCode(String errorCode) {
    Optional.ofNullable(errorCode)
        .filter(StringUtils::isNotBlank)
        .ifPresent(actualErrorCode -> this.errorCode = actualErrorCode);
    return this;
  }

  public ApiExceptionBuilder title(String title) {
    Optional.ofNullable(title)
        .filter(StringUtils::isNotBlank)
        .ifPresent(actualTitle -> this.title = actualTitle);
    return this;
  }

  public ApiExceptionDetailBuilder addDetail() {
    return new ApiExceptionDetailBuilder(this);
  }

  public ApiExceptionBuilder cause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  public ApiExceptionBuilder status(HttpStatus status) {
    Optional.ofNullable(status)
        .ifPresent(actualStatus -> this.status = actualStatus);
    return this;
  }

  public List<ApiExceptionDetail> getDetails() {
    return this.details;
  }

  public ApiException build() {
    if (Objects.nonNull(this.cause)) {
      if (this.cause instanceof ApiException) {
        ((ApiException)this.cause).getDetails()
            .forEach(detail -> this.addDetail()
              .withComponent(detail.getComponent())
              .withTitle(detail.getTitle()));
      } else {
        this.addDetail()
            .withComponent("Nombre de la api")
            .withTitle(this.cause.getClass().getName().concat(Optional.ofNullable(this.cause.getMessage())
                .map(" : "::concat)
                .orElse(StringUtils.EMPTY)))
            .push();
      }
      this.cause(null);
    }

    return new ApiException(this.type, this.title, this.errorCode, new ArrayList<>(this.details), this.cause, this.status);
  }

}
