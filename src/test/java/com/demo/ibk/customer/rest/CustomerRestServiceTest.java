package com.demo.ibk.customer.rest;

import static com.demo.ibk.customer.JsonFileReader.readListFromFile;
import static com.demo.ibk.customer.JsonFileReader.readObjectFromFile;
import static com.demo.ibk.customer.enums.DocumentType.DNI;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.demo.ibk.commons.properties.ApplicationProperties;
import com.demo.ibk.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.customer.service.CustomerService;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(value = CustomerRestService.class)
@ActiveProfiles("test") //use application-test.yaml
class CustomerRestServiceTest {

  @Autowired
  private MockMvc mockMvc;

  private static final Gson gson = new Gson();
  private static final String URI = "/ibk/v1/customers";

  @MockBean
  private ApplicationProperties applicationProperties;

  @MockBean
  private CustomerService customerService;

  @BeforeEach
  public void setUp() {
    MockConfig.mockProperties(applicationProperties);
    MockConfig.mockCustomerService(customerService);
  }

  @Test
  @DisplayName("Then return all customers")
  void thenReturnAllCustomers() throws Exception {
    //Arrange
    String expectedJson = gson.toJson(MockConfig.CUSTOMER_RESPONSE_DTO_LIST);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI)
        .accept(APPLICATION_JSON);

    //Act
    MockHttpServletResponse response = mockMvc
      .perform(requestBuilder)
      .andReturn()
      .getResponse();

    String actualJson = response.getContentAsString(UTF_8);

    //Assert
    assertEquals(expectedJson, actualJson);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("When search customers by document type, then return filtered customers")
  void whenSearchCustomersByDocumentType_thenReturnFilteredCustomers() throws Exception {
    //Arrange
    String expectedJson = gson.toJson(MockConfig.CUSTOMER_RESPONSE_DTO_LIST_BY_DOCUMENT_TYPE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI)
        .queryParam("documentType", "DNI")
        .accept(APPLICATION_JSON);

    //Act
    MockHttpServletResponse response = mockMvc
      .perform(requestBuilder)
      .andReturn()
      .getResponse();

    String actualJson = response.getContentAsString(UTF_8);

    //Assert
    assertEquals(expectedJson, actualJson);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("When search customer by unique code, then return the expected customer")
  void whenSearchCustomerByUniqueCode_thenReturnExpectedCustomer() throws Exception {
    //Arrange
    String expectedJson = gson.toJson(MockConfig.CUSTOMER_RESPONSE_DTO);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI.concat("/7"))
        .accept(APPLICATION_JSON);

    //Act
    MockHttpServletResponse response = mockMvc
      .perform(requestBuilder)
      .andReturn()
      .getResponse();

    String actualJson = response.getContentAsString(UTF_8);

    //Assert
    assertEquals(expectedJson, actualJson);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("When delete a customer, then customer is deleted")
  void whenDeleteCustomer_thenCustomerIsDeleted() throws Exception {
    //Arrange
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(URI.concat("/7"));

    //Act
    MockHttpServletResponse response = mockMvc
      .perform(requestBuilder)
      .andReturn()
      .getResponse();

    //Assert
    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MockConfig {

    public static CustomerResponseDto CUSTOMER_RESPONSE_DTO = readObjectFromFile(CustomerResponseDto.class, "mocks/customer/CustomerResponseDto.json");

    public static List<CustomerResponseDto> CUSTOMER_RESPONSE_DTO_LIST = readListFromFile(CustomerResponseDto.class, "mocks/customer/CustomerResponseDto_List.json");

    public static List<CustomerResponseDto> CUSTOMER_RESPONSE_DTO_LIST_BY_DOCUMENT_TYPE = CUSTOMER_RESPONSE_DTO_LIST.stream()
      .filter(customer -> customer.getDocumentType().equals(DNI.name()))
      .collect(Collectors.toList());

    public static void mockProperties(ApplicationProperties properties) {
      when(properties.getErrorMessages())
        .thenReturn(Map.of("Default", "No hemos podido realizar tu operación. Estamos trabajando para solucionar el inconveniente."));
    }

    public static void mockCustomerService(CustomerService customerService) {
      when(customerService.findByUniqueCode(anyLong()))
        .thenReturn(CUSTOMER_RESPONSE_DTO);

      when(customerService.findByDocumentType(anyString()))
        .thenReturn(CUSTOMER_RESPONSE_DTO_LIST_BY_DOCUMENT_TYPE);

      when(customerService.findByDocumentType(null))
        .thenReturn(CUSTOMER_RESPONSE_DTO_LIST);
    }
  }
}
