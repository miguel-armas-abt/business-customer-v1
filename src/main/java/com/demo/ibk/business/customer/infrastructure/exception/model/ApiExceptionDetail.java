package com.demo.ibk.business.customer.infrastructure.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * title(A human-readable explanation specific to this occurrence of the problem)
 * component(The component where the error is coming from)
 */
@Getter
@Data
@Builder
@AllArgsConstructor
public class ApiExceptionDetail implements Serializable {

  private String title;
  private String component;

}
