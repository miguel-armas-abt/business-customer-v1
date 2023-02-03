package com.demo.ibk.business.customer.infrastructure.exception.model;

import com.demo.ibk.business.customer.infrastructure.exception.builder.ApiExceptionBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <br/>Clase modelo que define el objeto de excepción personalizada.<br/>
 *
 * <b>Class</b>: ApiException<br/>
 *
 * @author Miguel Armas Abt <br/>
 *      <u>Developed by</u>: <br/>
 *      <ul>
 *      <li>Miguel Armas Abt</li>
 *      </ul>
 *      <u>Changes</u>:<br/>
 *      <ul>
 *      <li>Oct, 2021 Creación de Clase.</li>
 *      </ul>
 * @version 1.0
 */
@Getter
@Setter
public class ApiException extends RuntimeException {

  private String type;
  private String title;
  private String errorCode;
  private List<ApiExceptionDetail> details;
  private Throwable cause;
  private HttpStatus status;

  public ApiException(String type, String title, String errorCode, List<ApiExceptionDetail> details, Throwable cause,
                      HttpStatus status) {

    super(title, cause);
    this.type = type;
    this.title = title;
    this.errorCode = errorCode;
    this.details = Optional.ofNullable(details)
        .map(Collections::unmodifiableList)
        .orElseGet(Collections::emptyList);
    this.status = status;
  }

  public static ApiExceptionBuilder builder(String type, String errorCode, String title, HttpStatus status) {
    return new ApiExceptionBuilder()
        .type(type)
        .title(title)
        .errorCode(errorCode)
        .status(status);
  }

  public List<ApiExceptionDetail> getDetails() {
    if (this.getCause() instanceof ApiException) {
      List<ApiExceptionDetail> details = ((ApiException)this.getCause()).getDetails();
      List<ApiExceptionDetail> newDetails = new ArrayList<>();
      newDetails.addAll(this.details);
      newDetails.addAll(details);
      return Collections.unmodifiableList(newDetails);
    } else {
      return this.details;
    }
  }

}
