package com.demo.ibk.business.customer.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.infrastructure.repository.database.entity.CustomerEntity;
import com.google.gson.Gson;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerMapperTest {

    private CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    private CustomerEntity customerEntity;

    private Customer customer;

    private CustomerRequest customerRequest;

    /**
     * Método que pre carga la data necesaria para ejecutar las pruebas unitarias.
     */
    @BeforeEach
    public void setup() throws IOException {

        customerEntity = JsonFileReader
                .getAnElement("data/model/customer/entity/CustomerEntity.json", CustomerEntity.class);

        customer = JsonFileReader
            .getAnElement("data/model/customer/dto/response/Customer.json", Customer.class);

        customerRequest = JsonFileReader
            .getAnElement("data/model/customer/dto/request/CustomerRequest.json", CustomerRequest.class);
    }

    @Test
    @DisplayName("return response from entity")
    public void returnResponseFromEntity() {
        String expected = new Gson().toJson(customer);
        String actual = new Gson().toJson(mapper.entityToResponse(customerEntity));

        assertEquals(expected, actual);
    }

    @Test
    public void returnRequestFromEntity() {
        CustomerEntity currentCustomerEntity = customerEntity;
        customerEntity.setId(null);

        String expected = new Gson().toJson(currentCustomerEntity);
        String actual = new Gson().toJson(mapper.requestToEntity(customerRequest));

        assertEquals(expected, actual);
    }

}