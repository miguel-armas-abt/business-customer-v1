package com.demo.ibk.business.customer.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.demo.ibk.business.customer.JsonFileReader.readObjectFromFile;

import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.repository.entity.CustomerEntity;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CustomerMapperTest {

  private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

  private static final Gson gson = new Gson();
  private static CustomerEntity CUSTOMER_ENTITY;
  private static CustomerResponseDto CUSTOMER_RESPONSE_DTO;
  private static CustomerRequestDto CUSTOMER_REQUEST_DTO;

  @BeforeEach
  public void setup() {
    CUSTOMER_ENTITY = readObjectFromFile(CustomerEntity.class, "mocks/customer/CustomerEntity.json");
    CUSTOMER_RESPONSE_DTO = readObjectFromFile(CustomerResponseDto.class, "mocks/customer/CustomerResponseDto.json");
    CUSTOMER_REQUEST_DTO = readObjectFromFile(CustomerRequestDto.class, "mocks/customer/CustomerRequestDto.json");
  }

  @Test
  @DisplayName("Given an entity, when mapping object, then return responseDto")
  void givenEntity_whenMappingObject_ThenReturnResponseDto() {
    //Arrange
    String expectedJson = gson.toJson(CUSTOMER_RESPONSE_DTO);

    //Act
    CustomerResponseDto actual = mapper.toResponseDto(CUSTOMER_ENTITY);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("Given an entity, when mapping object, then return requestDto")
  void givenEntity_whenMappingObject_ThenReturnRequestDto() {
    //Arrange
    CUSTOMER_ENTITY.setId(null);
    String expectedJson = gson.toJson(CUSTOMER_ENTITY);

    //Act
    CustomerEntity actual = mapper.toEntity(CUSTOMER_REQUEST_DTO);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }
}
