package com.demo.poc.customer.mapper;

import com.demo.poc.customer.dto.request.CustomerRequestDto;
import com.demo.poc.customer.dto.response.CustomerResponseDto;
import com.demo.poc.customer.repository.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerResponseDto toResponseDto(CustomerEntity customerEntity);

  @Mapping(target = "password", source = "cipheredPassword")
  CustomerEntity toEntity(CustomerRequestDto menuOption, String cipheredPassword);
}
