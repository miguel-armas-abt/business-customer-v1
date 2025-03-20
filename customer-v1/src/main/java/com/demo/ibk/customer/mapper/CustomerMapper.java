package com.demo.ibk.customer.mapper;

import com.demo.ibk.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.customer.repository.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerResponseDto toResponseDto(CustomerEntity customerEntity);

  @Mapping(target = "password", source = "cipheredPassword")
  CustomerEntity toEntity(CustomerRequestDto menuOption, String cipheredPassword);
}
