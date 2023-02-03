package com.demo.ibk.business.customer.infrastructure.mapper;

import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.demo.ibk.business.customer.infrastructure.repository.database.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  Customer entityToResponse(CustomerEntity customerEntity);
  CustomerEntity requestToEntity(CustomerRequest menuOption);
}
