package com.demo.ibk.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.demo.ibk.customer.repository.customer.CustomerRepository;
import com.demo.ibk.customer.repository.customer.entity.CustomerEntity;
import com.demo.ibk.customer.JsonFileReader;
import com.demo.ibk.customer.enums.DocumentType;
import com.google.gson.Gson;
import java.util.List;
import java.util.Optional;
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
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  private static final Gson gson = new Gson();

  private static List<CustomerEntity> CUSTOMER_ENTITY_LIST;

  @BeforeEach
  void setup() {
    CUSTOMER_ENTITY_LIST = JsonFileReader.readListFromFile(CustomerEntity.class, "mocks/customer/CustomerEntity_List.json");
  }

  @Test
  @DisplayName("Given a populated database, when search all, then return all customers")
  void givenPopulatedDatabase_whenSearchAllCustomers_thenReturnAllCustomers() {
    //Arrange
    String expectedJson = gson.toJson(CUSTOMER_ENTITY_LIST);

    //Act
    List<CustomerEntity> actual = repository.findAll();
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("Given a populated database, when search a customer by ID, then return the expected customer")
  void givenPopulatedDatabase_whenSearchCustomerById_thenReturnExpectedCustomer() {
    //Arrange
    CustomerEntity expectedCustomer = CUSTOMER_ENTITY_LIST.get(0);
    String expectedJson = gson.toJson(expectedCustomer);

    //Act
    Optional<CustomerEntity> actual = repository.findById(1L);
    String actualJson = gson.toJson(actual.get());

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("Given a populated database, when search a customer by document type, then return filtered customers")
  void givenPopulatedDatabase_whenSearchCustomerByDocumentType_thenReturnFilteredCustomers() {
    //Arrange
    List<CustomerEntity> expectedCustomers = CUSTOMER_ENTITY_LIST.stream()
        .filter(customer -> customer.getDocumentType().equals(DocumentType.DNI.name()))
        .collect(Collectors.toList());

    String expectedJson = gson.toJson(expectedCustomers);

    //Act
    List<CustomerEntity> actual = repository.findByDocumentType(DocumentType.DNI.name());
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("Given a populated database, when delete customer by ID, then delete the expected customer")
  void givenPopulatedDatabase_whenDeleteCustomerById_thenDeleteExpectedCustomer() {
    //Arrange
    Long id = 1L;
    boolean existsBefore = repository.existsById(id);
    int rowsNumberBefore = repository.findAll().size();

    //Act
    repository.deleteById(1L);

    boolean isExistAfter = repository.existsById(id);
    int rowsNumberAfter = repository.findAll().size();

    //Assert
    assertEquals(rowsNumberAfter + 1, rowsNumberBefore);
    assertTrue(existsBefore);
    assertFalse(isExistAfter);
  }

}
