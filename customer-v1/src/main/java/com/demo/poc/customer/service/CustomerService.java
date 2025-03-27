package com.demo.poc.customer.service;

import java.util.List;
import com.demo.poc.customer.dto.request.CustomerRequestDto;
import com.demo.poc.customer.dto.response.CustomerResponseDto;

public interface CustomerService {

  List<CustomerResponseDto> findByDocumentType(String documentType);

  CustomerResponseDto findByUniqueCode(Long uniqueCode);

  Long save (CustomerRequestDto customerRequest);

  Long update(Long uniqueCode, CustomerRequestDto customerRequest);

  Long deleteByUniqueCode(Long uniqueCode);
}
