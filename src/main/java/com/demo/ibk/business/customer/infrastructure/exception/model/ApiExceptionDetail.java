package com.demo.ibk.business.customer.infrastructure.exception.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "A human-readable explanation specific to this occurrence of the problem", example = "The customer does not exist")
  private String title;

  @Schema(description = "The component where the error is coming from", example = "business-customer-v1")
  private String component;

}
