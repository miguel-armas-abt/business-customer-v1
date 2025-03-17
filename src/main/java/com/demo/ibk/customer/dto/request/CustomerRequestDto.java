package com.demo.ibk.customer.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class CustomerRequestDto implements Serializable {

  private Long uniqueCode;

  @Size(min = 3, max = 300)
  @NotNull(message = "names cannot be null")
  private String names;

  @Size(min = 3, max = 300)
  @NotNull(message = "lastNames cannot be null")
  private String lastNames;

  @Pattern(regexp = "^(DNI|CE|PASSPORT)$", message = "documentType has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "documentType cannot be null")
  private String documentType;

  @NotNull(message = "documentNumber cannot be null")
  private Integer documentNumber;

  @NotNull(message = "is active cannot be null")
  private Boolean active;

}
