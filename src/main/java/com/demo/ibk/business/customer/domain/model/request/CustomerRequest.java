package com.demo.ibk.business.customer.domain.model.request;

import com.demo.ibk.business.customer.domain.constant.CustomerRegex;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CustomerRequest implements Serializable {

  @Schema(description = "Customer unique code", example = "1")
  private Long uniqueCode;

  @Schema(description = "Customer's names", example = "Miguel Rodrigo")
  @Pattern(regexp = CustomerRegex.ANY_STRING, message = "name has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "names cannot be null")
  private String names;

  @Schema(description = "Customer's last names", example = "Armas Abt")
  @Pattern(regexp = CustomerRegex.ANY_STRING, message = "lastNames has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "lastNames cannot be null")
  private String lastNames;

  @Schema(description = "Identification document type", example = "DNI", allowableValues = {"DNI", "IMMIGRATION", "RUC", "PASSPORT"})
  @Pattern(regexp = CustomerRegex.DOCUMENT_TYPE, message = "documentType has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "documentType cannot be null")
  private String documentType;

  @Schema(description = "Identification document number", example = "76517368")
  @NotNull(message = "documentNumber cannot be null")
  private Integer documentNumber;

  @Schema(description = "Flag that indicates logical elimination", example = "false")
  @NotNull(message = "is active cannot be null")
  private boolean active;

}