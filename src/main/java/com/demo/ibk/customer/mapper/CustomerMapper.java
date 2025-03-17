package com.demo.ibk.customer.mapper;

import com.demo.ibk.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.customer.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerResponseDto toResponseDto(CustomerEntity customerEntity);

  CustomerEntity toEntity(CustomerRequestDto menuOption);
}
