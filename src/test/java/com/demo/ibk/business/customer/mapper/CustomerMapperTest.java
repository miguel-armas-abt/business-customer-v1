package com.demo.ibk.business.customer.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.ibk.business.customer.JsonFileReader;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.repository.entity.CustomerEntity;
import com.google.gson.Gson;
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

    private CustomerResponseDto customerResponseDto;

    private CustomerRequestDto customerRequestDto;

    /**
     * Método que pre carga la data necesaria para ejecutar las pruebas unitarias.
     */
    @BeforeEach
    public void setup() {
        customerEntity = JsonFileReader.readObjectFromFile(CustomerEntity.class, "mocks/entity/CustomerEntity.json");

        customerResponseDto = JsonFileReader.readObjectFromFile(CustomerResponseDto.class, "mocks/dto/response/CustomerResponseDto.json");

        customerRequestDto = JsonFileReader.readObjectFromFile(CustomerRequestDto.class, "mocks/dto/request/CustomerRequestDto.json");
    }

    @Test
    @DisplayName("return response from entity")
    public void returnResponseFromEntity() {
        String expected = new Gson().toJson(customerResponseDto);
        String actual = new Gson().toJson(mapper.toResponseDto(customerEntity));

        assertEquals(expected, actual);
    }

    @Test
    public void returnRequestFromEntity() {
        CustomerEntity currentCustomerEntity = customerEntity;
        customerEntity.setId(null);

        String expected = new Gson().toJson(currentCustomerEntity);
        String actual = new Gson().toJson(mapper.toEntity(customerRequestDto));

        assertEquals(expected, actual);
    }
}
