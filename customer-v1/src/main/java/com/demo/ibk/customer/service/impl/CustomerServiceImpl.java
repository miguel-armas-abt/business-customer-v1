package com.demo.ibk.customer.service.impl;

import com.demo.ibk.commons.errors.exceptions.CustomerAlreadyExistsException;
import com.demo.ibk.commons.errors.exceptions.CustomerNotFoundException;
import com.demo.ibk.customer.repository.cryptography.CryptographyRepository;
import com.demo.ibk.customer.service.CustomerService;
import com.demo.ibk.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.customer.mapper.CustomerMapper;
import com.demo.ibk.customer.repository.customer.CustomerRepository;
import com.demo.ibk.customer.enums.DocumentType;
import com.demo.ibk.customer.repository.customer.entity.CustomerEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final CryptographyRepository cryptographyRepository;

  @Override
  public List<CustomerResponseDto> findByDocumentType(String documentType) {
    return (Optional.ofNullable(documentType).isEmpty()
          ? customerRepository.findAll()
          : this.validateCustomerAndFindByDocumentType(documentType))
        .stream()
        .map(customerMapper::toResponseDto)
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
  public Long save(CustomerRequestDto customerRequest) {
    if (customerRepository.findByUniqueCode(customerRequest.getUniqueCode()).isPresent()) {
      throw new CustomerAlreadyExistsException();
    }
    String cipheredPassword = cryptographyRepository.encrypt(customerRequest.getPassword());
    return customerRepository.save(customerMapper.toEntity(customerRequest, cipheredPassword)).getUniqueCode();
  }

  @Override
  public Long update(Long uniqueCode, CustomerRequestDto customerRequest) {
    return customerRepository.findByUniqueCode(uniqueCode)
      .map(customerFound -> {
        String cipheredPassword = cryptographyRepository.encrypt(customerRequest.getPassword());

        CustomerEntity customerEntity = customerMapper.toEntity(customerRequest, cipheredPassword);
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
