package com.demo.ibk.business.customer.infrastructure.resource.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.application.service.CustomerService;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerApi.class)
@ActiveProfiles("test") //use application-test.yaml
public class CustomerApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  private String URI;
  private List<Customer> expectedSavedCustomerList;

  @Before
  public void setup() throws IOException {
    expectedSavedCustomerList = JsonFileReader
        .getList("data/model/customer/dto/response/Customer-List.json", Customer[].class);

    URI = "/ibk/business/v1/customers";
  }

  @Test
  @DisplayName("Return all customers")
  public void returnAllCustomers() throws Exception {
    when(customerService.findByDocumentType(null)).thenReturn(expectedSavedCustomerList);
    String expected = new Gson().toJson(expectedSavedCustomerList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI)
        .accept(APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    String actual = response.getContentAsString(UTF_8);

    assertEquals(expected, actual);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("Return customers filtered by document type")
  public void returnCustomersFilteredByDocumentType() throws Exception {
    List<Customer> expectedFilteredCustomerList = expectedSavedCustomerList
        .stream()
        .filter(customer -> customer.getDocumentType().equals("DNI"))
        .collect(Collectors.toList());
    when(customerService.findByDocumentType("DNI")).thenReturn(expectedFilteredCustomerList);
    String expected = new Gson().toJson(expectedFilteredCustomerList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI)
        .queryParam("documentType", "DNI")
        .accept(APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    String actual = response.getContentAsString(UTF_8);

    assertEquals(expected, actual);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("Return customer filtered by unique code")
  public void returnCustomerFilteredByUniqueCode() throws Exception {
    Customer expectedCustomer = JsonFileReader
        .getAnElement("data/model/customer/dto/response/Customer.json", Customer.class);
    when(customerService.findByUniqueCode(anyLong())).thenReturn(expectedCustomer);
    String expected = new Gson().toJson(expectedCustomer);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(URI.concat("/7"))
        .accept(APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    String actual = response.getContentAsString(UTF_8);

    assertEquals(expected, actual);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  @DisplayName("Save a customer")
  public void saveCustomer() throws Exception {
    CustomerRequest requestBody = JsonFileReader
        .getAnElement("data/model/customer/dto/request/CustomerRequest.json", CustomerRequest.class);
    String jsonRequestBody = new Gson().toJson(requestBody);
    when(customerService.save(any(CustomerRequest.class))).thenReturn(7L);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(URI)
        .accept(APPLICATION_JSON)
        .content(jsonRequestBody)
        .contentType(APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals("http://localhost/ibk/business/v1/customers/7", response.getHeader("Location"));
  }

  @Test
  public void updateCustomer() throws Exception {
    CustomerRequest requestBody = JsonFileReader
        .getAnElement("data/model/customer/dto/request/CustomerRequest.json", CustomerRequest.class);
    String jsonRequestBody = new Gson().toJson(requestBody);
    when(customerService.update(anyLong(), any(CustomerRequest.class))).thenReturn(7L);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(URI.concat("/7"))
        .accept(APPLICATION_JSON)
        .content(jsonRequestBody)
        .contentType(APPLICATION_JSON);

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
  }

  @Test
  @DisplayName("Delete a customer")
  public void deleteCustomer() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(URI.concat("/7"));

    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
  }
}