package com.demo.ibk.business.customer.domain.model.request;

import com.demo.ibk.business.customer.domain.constant.CustomerRegex;
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
public class CustomerRequest implements Serializable {

  private Long uniqueCode;

  @Pattern(regexp = CustomerRegex.ANY_STRING, message = "name has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "names cannot be null")
  private String names;

  @Pattern(regexp = CustomerRegex.ANY_STRING, message = "lastNames has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "lastNames cannot be null")
  private String lastNames;

  @Pattern(regexp = CustomerRegex.DOCUMENT_TYPE, message = "documentType has invalid format")
  @Size(min = 3, max = 300)
  @NotNull(message = "documentType cannot be null")
  private String documentType;

  @NotNull(message = "documentNumber cannot be null")
  private Integer documentNumber;

  @NotNull(message = "is active cannot be null")
  private boolean active;

}