package com.demo.ibk.business.customer.infrastructure.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Clase DTO que define el modelo de objeto para transmitir información de la excepción personalizada.
 *
 * type(notes: The unique uri identifier that categorizes the error, example: /errors/authentication)
 * title(notes: A brief, human-readable message about the error, example: The user does not have autorization)
 * errorCode(notes: The unique error code, example: 192)
 * details(notes: A human-readable explanation specific to this occurrence of the problem)
 * <br/>
 *
 * <b>Class</b>: ApiExceptionDto<br/>
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
@Builder
public class ApiExceptionResponse implements Serializable {

  @JsonProperty(value = "type")
  private final String type;

  @JsonProperty(value = "title")
  private final String title;

  @JsonProperty(value = "errorCode")
  private final String errorCode;

  @JsonProperty("details")
  private final List<ApiExceptionDetail> details;

  public static ApiExceptionResponseBuilder builder(String type, String title, String errorCode) {
    return new ApiExceptionResponseBuilder()
        .type(type)
        .errorCode(errorCode)
        .title(title)
        .details(Collections.emptyList());
  }

}
