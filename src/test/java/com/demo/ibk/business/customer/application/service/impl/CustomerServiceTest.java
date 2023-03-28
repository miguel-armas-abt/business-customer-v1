package com.demo.ibk.business.customer.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.demo.ibk.business.customer.infrastructure.mapper.CustomerMapper;
import com.demo.ibk.business.customer.infrastructure.repository.database.CustomerRepository;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.infrastructure.repository.database.entity.CustomerEntity;
import com.google.gson.Gson;
import java.io.IOException;
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
public class CustomerServiceTest {

  @InjectMocks
  private CustomerServiceImpl menuOptionService;

  @Mock
  private CustomerRepository customerRepository;

  @Spy
  private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

  private List<CustomerEntity> expectedSavedCustomerEntityList;

  private CustomerEntity expectedSavedCustomerEntity;

  private List<Customer> expectedSavedCustomerList;

  private Customer expectedSavedCustomer;

  private CustomerRequest customerRequest;

  @Before
  public void setup() throws IOException {
    expectedSavedCustomerEntityList = JsonFileReader
        .getList("data/model/customer/entity/CustomerEntity-List.json", CustomerEntity[].class);

    expectedSavedCustomerEntity = JsonFileReader.getAnElement(
        "data/model/customer/entity/CustomerEntity.json", CustomerEntity.class);

    expectedSavedCustomerList = JsonFileReader
            .getList("data/model/customer/dto/response/Customer-List.json", Customer[].class);

    expectedSavedCustomer = JsonFileReader.getAnElement(
        "data/model/customer/dto/response/Customer.json", Customer.class);

    customerRequest = JsonFileReader.getAnElement(
        "data/model/customer/dto/request/CustomerRequest.json", CustomerRequest.class);
  }

  @Test
  @DisplayName("Return all customers")
  public void returnAllCustomers() {
    when(customerRepository.findAll()).thenReturn(expectedSavedCustomerEntityList);

    String expected = new Gson().toJson(expectedSavedCustomerList);
    String actual = new Gson().toJson(menuOptionService.findByDocumentType(null));
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Return customer filtered by unique code")
  public void returnCustomerFilteredByUniqueCode() {
    when(customerRepository.findByUniqueCode(anyLong())).thenReturn(Optional.of(expectedSavedCustomerEntity));

    String expected = new Gson().toJson(expectedSavedCustomer);
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

    String expected = new Gson().toJson(expectedSavedCustomerList
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
    customerRequest.setUniqueCode(7L);
    String actual = new Gson().toJson(menuOptionService.update(7L, customerRequest));

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Save a customer")
  public void saveCustomer() {
    expectedSavedCustomerEntity.setUniqueCode(7L);
    when(customerRepository.save(any())).thenReturn(expectedSavedCustomerEntity);

    String expected = new Gson().toJson(expectedSavedCustomerEntity.getUniqueCode());
    String actual = new Gson().toJson(menuOptionService.save(customerRequest));

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Delete a customer")
  public void deleteCustomer() {
    when(customerRepository.findByUniqueCode(anyLong())).thenReturn(Optional.of(expectedSavedCustomerEntity));
    menuOptionService.deleteByUniqueCode(7L);
  }

}