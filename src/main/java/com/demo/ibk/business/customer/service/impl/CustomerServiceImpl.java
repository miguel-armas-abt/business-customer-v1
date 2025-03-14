package com.demo.ibk.business.customer.service.impl;

import com.demo.ibk.business.commons.errors.exceptions.CustomerNotFoundException;
import com.demo.ibk.business.customer.service.CustomerService;
import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.mapper.CustomerMapper;
import com.demo.ibk.business.customer.repository.CustomerRepository;
import com.demo.ibk.business.customer.enums.DocumentType;
import com.demo.ibk.business.customer.repository.entity.CustomerEntity;
import com.google.gson.Gson;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public List<CustomerResponseDto> findByDocumentType(String documentType) {
    return (Optional.ofNullable(documentType).isEmpty()
          ? customerRepository.findAll()
          : this.validateCustomerAndFindByDocumentType(documentType))
        .stream()
        .map(customerMapper::toResponseDto)
        .peek(menuOption -> log.info("findByDocumentType: {}", new Gson().toJson(menuOption)))
        .collect(Collectors.toList());
  }

  private List<CustomerEntity> validateCustomerAndFindByDocumentType(String documentType) {
    DocumentType.validateDocumentType.accept(documentType);
    return customerRepository.findByDocumentType(documentType);
  }

  @Override
  public CustomerResponseDto findByUniqueCode(Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(customerMapper::toResponseDto)
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public Long save(CustomerRequestDto menuOption) {
    if (customerRepository.findByUniqueCode(menuOption.getUniqueCode()).isPresent()) {
      throw new CustomerNotFoundException();
    }
    return customerRepository.save(customerMapper.toEntity(menuOption)).getUniqueCode();
  }

  @Override
  public Long update(Long uniqueCode, CustomerRequestDto customerRequestDto) {
    return customerRepository.findByUniqueCode(uniqueCode)
      .map(customerFound -> {
        CustomerEntity customerEntity = customerMapper.toEntity(customerRequestDto);
        customerEntity.setId(customerFound.getId());
        customerEntity.setUniqueCode(customerFound.getUniqueCode());
        customerRepository.save(customerEntity);
        return customerEntity.getUniqueCode();
      })
      .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public Long deleteByUniqueCode(Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(menuOptionFound -> {
          customerRepository.deleteById(menuOptionFound.getId());
          return menuOptionFound.getUniqueCode();
        })
        .orElseThrow(CustomerNotFoundException::new);
  }

}
