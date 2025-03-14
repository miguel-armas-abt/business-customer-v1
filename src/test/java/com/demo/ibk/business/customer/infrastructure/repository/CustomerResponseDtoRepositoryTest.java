package com.demo.ibk.business.customer.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.repository.CustomerRepository;
import com.demo.ibk.business.customer.repository.entity.CustomerEntity;
import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

// @AutoConfigureTestDatabase(replace = NONE) //use real database
@DataJpaTest
@ActiveProfiles("test") //use application-test.yaml
public class CustomerResponseDtoRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  private List<CustomerEntity> expectedCustomerEntityList;

  @BeforeEach
  void setup() {
    expectedCustomerEntityList = JsonFileReader.readListFromFile(CustomerEntity.class, "data/model/customer/entity/CustomerEntity-List.json");
  }

  @Test
  @DisplayName("Return all customers")
  public void returnAllCustomers() {
    String expected = new Gson().toJson(expectedCustomerEntityList);
    String actual = new Gson().toJson(repository.findAll());
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Return customer by id")
  public void returnCustomerById() {
    CustomerEntity expectedCustomerEntity = expectedCustomerEntityList.get(0);
    String expected = new Gson().toJson(expectedCustomerEntity);
    String actual = new Gson().toJson(repository.findById(1L).get());
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Return customers by document type")
  public void returnCustomersByDocumentType() {
    List<CustomerEntity> expectedCustomerEntityListFilteredByDni = expectedCustomerEntityList
        .stream()
        .filter(customerFound -> customerFound.getDocumentType().equals("DNI"))
        .collect(Collectors.toList());

    String expected = new Gson().toJson(expectedCustomerEntityListFilteredByDni);
    String actual = new Gson().toJson(repository.findByDocumentType("DNI"));
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Delete a customer")
  public void deleteById() {
    int rowsNumberBefore = repository.findAll().size();
    Long id = 1L;
    boolean isExistBefore = repository.findById(id).isPresent();
    repository.deleteById(1L);
    int rowsNumberAfter = repository.findAll().size();
    boolean isExistAfter = repository.findById(id).isPresent();

    assertEquals(rowsNumberBefore, rowsNumberAfter + 1);
    assertTrue(isExistBefore);
    assertFalse(isExistAfter);
  }

}
