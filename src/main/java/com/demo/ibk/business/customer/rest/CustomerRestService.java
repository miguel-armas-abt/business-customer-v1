package com.demo.ibk.business.customer.rest;

import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.service.CustomerService;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ibk/business/v1/customers")
public class CustomerRestService {

  private final CustomerService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{uniqueCode}")
  public ResponseEntity<CustomerResponseDto> findByUniqueCode(@PathVariable(name = "uniqueCode") Long uniqueCode) {
    return ResponseEntity.ok(service.findByUniqueCode(uniqueCode));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CustomerResponseDto>> findByDocumentType(@RequestParam(value = "documentType", required = false) String documentType) {

    List<CustomerResponseDto> customerResponseDtoList = service.findByDocumentType(documentType);
    return (customerResponseDtoList == null || customerResponseDtoList.isEmpty())
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(service.findByDocumentType(documentType));
  }

  @PostMapping
  public ResponseEntity<Void> save(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
    Long uniqueCode = service.save(customerRequestDto);
    return ResponseEntity.created(buildUriLocation.apply(uniqueCode)).build();
  }

  @PutMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> update(@Valid @RequestBody CustomerRequestDto customerRequestDto,
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    Long updatedCustomerUniqueCode = service.update(uniqueCode, customerRequestDto);
    return ResponseEntity.created(buildUriLocation.apply(updatedCustomerUniqueCode)).build();
  }

  @DeleteMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> delete(@PathVariable("uniqueCode") Long uniqueCode) {
    service.deleteByUniqueCode(uniqueCode);
    return ResponseEntity.noContent().location(buildUriLocation.apply(uniqueCode)).build();
  }

  private static final Function<Long, URI> buildUriLocation = uniqueCode ->
      ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{uniqueCode}")
      .buildAndExpand(uniqueCode)
      .toUri();
}
