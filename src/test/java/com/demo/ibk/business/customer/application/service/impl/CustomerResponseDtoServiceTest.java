package com.demo.ibk.business.customer.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.mapper.CustomerMapper;
import com.demo.ibk.business.customer.repository.CustomerRepository;
import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.repository.entity.CustomerEntity;
import com.demo.ibk.business.customer.service.impl.CustomerServiceImpl;
import com.google.gson.Gson;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResponseDtoServiceTest {

  @InjectMocks
  private CustomerServiceImpl menuOptionService;

  @Mock
  private CustomerRepository customerRepository;

  @Spy
  private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

  private List<CustomerEntity> expectedSavedCustomerEntityList;

  private CustomerEntity expectedSavedCustomerEntity;

  private List<CustomerResponseDto> expectedSavedCustomerListResponseDto;

  private CustomerResponseDto expectedSavedCustomerResponseDto;

  private CustomerRequestDto customerRequestDto;

  @Before
  public void setup() {
    expectedSavedCustomerEntityList = JsonFileReader.readListFromFile(CustomerEntity.class, "data/model/customer/entity/CustomerEntity-List.json");

    expectedSavedCustomerEntity = JsonFileReader.readObjectFromFile(CustomerEntity.class,  "data/model/customer/entity/CustomerEntity.json");

    expectedSavedCustomerListResponseDto = JsonFileReader.readListFromFile(CustomerResponseDto.class, "data/model/customer/dto/response/Customer-List.json");

    expectedSavedCustomerResponseDto = JsonFileReader.readObjectFromFile(CustomerResponseDto.class, "data/model/customer/dto/response/Customer.json");

    customerRequestDto = JsonFileReader.readObjectFromFile(CustomerRequestDto.class, "data/model/customer/dto/request/CustomerRequest.json");
  }

  @Test
  @DisplayName("Return all customers")
  public void returnAllCustomers() {
    when(customerRepository.findAll()).thenReturn(expectedSavedCustomerEntityList);

    String expected = new Gson().toJson(expectedSavedCustomerListResponseDto);
    String actual = new Gson().toJson(menuOptionService.findByDocumentType(null));
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Return customer filtered by unique code")
  public void returnCustomerFilteredByUniqueCode() {
    when(customerRepository.findByUniqueCode(anyLong())).thenReturn(Optional.of(expectedSavedCustomerEntity));

    String expected = new Gson().toJson(expectedSavedCustomerResponseDto);
    String actual = new Gson().toJson(menuOptionService.findByUniqueCode(1L));

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Return customers filtered by document type")
  public void returnCustomersFilteredByDocumentType() {
    when(customerRepository.findByDocumentType("DNI")).thenReturn(expectedSavedCustomerEntityList
            .stream()
            .filter(customerFound -> customerFound.getDocumentType().equals("DNI"))
            .collect(Collectors.toList()));

    String expected = new Gson().toJson(expectedSavedCustomerListResponseDto
            .stream()
            .filter(customer -> customer.getDocumentType().equals("DNI"))
            .collect(Collectors.toList()));
    String actual = new Gson().toJson(menuOptionService.findByDocumentType("DNI"));
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Update a customer")
  public void updateCustomer() {
    expectedSavedCustomerEntity.setUniqueCode(7L);
    when(customerRepository.findByUniqueCode(any())).thenReturn(Optional.of(expectedSavedCustomerEntity));
    when(customerRepository.save(any())).thenReturn(expectedSavedCustomerEntity);

    String expected = new Gson().toJson(expectedSavedCustomerEntity.getUniqueCode());
    customerRequestDto.setUniqueCode(7L);
    String actual = new Gson().toJson(menuOptionService.update(7L, customerRequestDto));

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Save a customer")
  public void saveCustomer() {
    expectedSavedCustomerEntity.setUniqueCode(7L);
    when(customerRepository.save(any())).thenReturn(expectedSavedCustomerEntity);

    String expected = new Gson().toJson(expectedSavedCustomerEntity.getUniqueCode());
    String actual = new Gson().toJson(menuOptionService.save(customerRequestDto));

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Delete a customer")
  public void deleteCustomer() {
    when(customerRepository.findByUniqueCode(anyLong())).thenReturn(Optional.of(expectedSavedCustomerEntity));
    menuOptionService.deleteByUniqueCode(7L);
  }

}
