package com.demo.ibk.business.customer.infrastructure.repository.database;

import com.demo.ibk.business.customer.infrastructure.repository.database.entity.CustomerEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

  List<CustomerEntity> findAll();

  Optional<CustomerEntity> findById(Long id);

  Optional<CustomerEntity> findByUniqueCode(Long uniqueCode);

  List<CustomerEntity> findByDocumentType(String documentType);

  CustomerEntity save(CustomerEntity customerEntity);

  void deleteById(Long id);
}