package com.demo.ibk.business.customer.service;

import java.util.List;
import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;

/**
 * <br/>Interface Service que define los métodos necesarios para tramitar la lógica de negocio del contexto Customer.<br/>
 *
 * @author Miguel Armas Abt <br/>
 */
public interface CustomerService {

  List<CustomerResponseDto> findByDocumentType(String documentType);

  CustomerResponseDto findByUniqueCode(Long uniqueCode);

  Long save (CustomerRequestDto customerRequest);

  Long update(Long uniqueCode, CustomerRequestDto customerRequest);

  Long deleteByUniqueCode(Long uniqueCode);
}
