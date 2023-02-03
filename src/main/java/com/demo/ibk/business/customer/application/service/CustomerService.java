package com.demo.ibk.business.customer.application.service;

import java.util.List;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.domain.model.response.Customer;

/**
 * <br/>Interface Service que define los métodos necesarios para tramitar la lógica de negocio del contexto Customer.<br/>
 *
 * @author Miguel Armas Abt <br/>
 */
public interface CustomerService {

  List<Customer> findByDocumentType(String documentType);

  Customer findByUniqueCode(Long uniqueCode);

  Long save (CustomerRequest menuOption);

  Long update(Long uniqueCode, CustomerRequest menuOption);

  Long deleteByUniqueCode(Long uniqueCode);
}
