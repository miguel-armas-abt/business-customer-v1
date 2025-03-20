package com.demo.ibk.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.demo.ibk.customer.enums.DocumentType.DNI;
import static com.demo.ibk.customer.JsonFileReader.readListFromFile;
import static com.demo.ibk.customer.JsonFileReader.readObjectFromFile;

import com.demo.ibk.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.customer.mapper.CustomerMapper;
import com.demo.ibk.customer.repository.CustomerRepository;
import com.demo.ibk.customer.repository.entity.CustomerEntity;
import com.demo.ibk.customer.service.impl.CustomerServiceImpl;
import com.google.gson.Gson;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

class CustomerServiceTest {

  private static CustomerService customerService;
  private static Gson gson;

  private List<CustomerResponseDto> CUSTOMER_RESPONSE_DTO_LIST;
  private CustomerResponseDto CUSTOMER_RESPONSE_DTO;

  @BeforeAll
  public static void init() {
    customerService = MockConfig.mockCustomerService();
    gson = new Gson();
  }

  @BeforeEach
  public void setup() {
    CUSTOMER_RESPONSE_DTO_LIST = readListFromFile(CustomerResponseDto.class, "mocks/customer/CustomerResponseDto_List.json");
    CUSTOMER_RESPONSE_DTO = readObjectFromFile(CustomerResponseDto.class, "mocks/customer/CustomerResponseDto.json");
  }

  @Test
  @DisplayName("Then return all customers")
  void thenReturnAllCustomers() {
    //Arrange
    String expectedJson = gson.toJson(CUSTOMER_RESPONSE_DTO_LIST);

    //Act
    List<CustomerResponseDto> actual = customerService.findByDocumentType(null);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("When search customer by unique code, then return the expected customer")
  void whenSearchCustomerByUniqueCode_thenReturnExpectedCustomer() {
    //Arrange
    String expected = gson.toJson(CUSTOMER_RESPONSE_DTO);

    //Act
    CustomerResponseDto actual = customerService.findByUniqueCode(1L);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expected, actualJson);
  }

  @Test
  @DisplayName("When search customers by document type, then return filtered customers")
  void whenSearchCustomersByDocumentType_thenReturnFilteredCustomers() {
    //Arrange
    List<CustomerResponseDto> expected = CUSTOMER_RESPONSE_DTO_LIST.stream()
      .filter(customer -> customer.getDocumentType().equals(DNI.name()))
      .collect(Collectors.toList());

    String expectedJson = gson.toJson(expected);

    //Act
    List<CustomerResponseDto> actual = customerService.findByDocumentType(DNI.name());
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class MockConfig {

    private static final List<CustomerEntity> CUSTOMER_ENTITY_ALL = readListFromFile(CustomerEntity.class, "mocks/customer/CustomerEntity_List.json");
    private static final CustomerEntity CUSTOMER_ENTITY_BY_UNIQUE_CODE = readObjectFromFile(CustomerEntity.class, "mocks/customer/CustomerEntity.json");

    public static CustomerService mockCustomerService() {
      CustomerRepository repository = mockCustomerRepository();
      CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
      return new CustomerServiceImpl(repository, mapper);
    }

    public static CustomerRepository mockCustomerRepository() {
      CustomerRepository repository = mock(CustomerRepository.class);

      when(repository.findAll())
        .thenReturn(CUSTOMER_ENTITY_ALL);

      when(repository.findByDocumentType(any()))
        .thenReturn(CUSTOMER_ENTITY_ALL.stream()
          .filter(customer -> customer.getDocumentType().equals(DNI.name()))
          .collect(Collectors.toList()));

      when(repository.findByUniqueCode(anyLong()))
        .thenReturn(Optional.of(CUSTOMER_ENTITY_BY_UNIQUE_CODE));

      return repository;
    }
  }

}
