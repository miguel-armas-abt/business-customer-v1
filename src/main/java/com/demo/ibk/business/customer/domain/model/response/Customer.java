package com.demo.ibk.business.customer.domain.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

  @Schema(description = "Customer unique code", example = "1")
  private Long uniqueCode;

  @Schema(description = "Customer's names", implementation = String.class)
  private String names;

  @Schema(description = "Customer's last names", example = "Armas Abt")
  private String lastNames;

  @Schema(description = "Identification document type", example = "DNI", allowableValues = {"DNI", "IMMIGRATION", "RUC", "PASSPORT"})
  private String documentType;

  @Schema(description = "Identification document number", example = "76517368")
  private Integer documentNumber;

}