package com.demo.ibk.business.customer.application.service.impl;

import com.demo.ibk.business.customer.application.service.CustomerService;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.demo.ibk.business.customer.infrastructure.logstash.Markers;
import com.demo.ibk.business.customer.infrastructure.mapper.CustomerMapper;
import com.demo.ibk.business.customer.infrastructure.repository.database.CustomerRepository;
import com.demo.ibk.business.customer.domain.catalog.DocumentTypeCatalog;
import com.demo.ibk.business.customer.domain.exception.CustomerException;
import com.demo.ibk.business.customer.infrastructure.repository.database.entity.CustomerEntity;
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
  public List<Customer> findByDocumentType(String documentType) {
    return (Optional.ofNullable(documentType).isEmpty()
          ? customerRepository.findAll()
          : this.validateCustomerAndFindByDocumentType(documentType))
        .stream()
        .map(customerMapper::entityToResponse)
        .peek(menuOption -> log.info(Markers.SENSITIVE_JSON, "findByDocumentType: {}", new Gson().toJson(menuOption)))
        .collect(Collectors.toList());
  }

  private List<CustomerEntity> validateCustomerAndFindByDocumentType(String documentType) {
    DocumentTypeCatalog.validateDocumentType.accept(documentType);
    return customerRepository.findByDocumentType(documentType);
  }

  @Override
  public Customer findByUniqueCode(Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(customerMapper::entityToResponse)
        .orElseThrow(CustomerException.ERROR1000::buildCustomException);
  }

  @Override
  public Long save(CustomerRequest menuOption) {
    if (customerRepository.findByUniqueCode(menuOption.getUniqueCode()).isPresent()) {
      throw CustomerException.ERROR1002.buildCustomException();
    }
    return customerRepository.save(customerMapper.requestToEntity(menuOption)).getUniqueCode();
  }

  @Override
  public Long update(Long uniqueCode, CustomerRequest customerRequest) {
    return customerRepository.findByUniqueCode(uniqueCode)
      .map(customerFound -> {
        CustomerEntity customerEntity = customerMapper.requestToEntity(customerRequest);
        customerEntity.setId(customerFound.getId());
        customerEntity.setUniqueCode(customerFound.getUniqueCode());
        customerRepository.save(customerEntity);
        return customerEntity.getUniqueCode();
      })
      .orElseThrow(CustomerException.ERROR1000::buildCustomException);
  }

  @Override
  public Long deleteByUniqueCode(Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(menuOptionFound -> {
          customerRepository.deleteById(menuOptionFound.getId());
          return menuOptionFound.getUniqueCode();
        })
        .orElseThrow(CustomerException.ERROR1000::buildCustomException);
  }

}
