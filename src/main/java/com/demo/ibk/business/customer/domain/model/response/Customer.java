package com.demo.ibk.business.customer.domain.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
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

  private Long uniqueCode;

  private String names;

  private String lastNames;

  private String documentType;

  private Integer documentNumber;

}