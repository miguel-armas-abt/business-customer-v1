package com.demo.ibk.business.customer.rest;

import com.demo.ibk.business.customer.dto.request.CustomerRequestDto;
import com.demo.ibk.business.customer.dto.response.CustomerResponseDto;
import com.demo.ibk.business.customer.service.CustomerService;
import java.net.URI;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
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
  public ResponseEntity<CustomerResponseDto> findByUniqueCode(HttpServletRequest servletRequest,
                                                              @PathVariable(name = "uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    return ResponseEntity.ok(service.findByUniqueCode(uniqueCode));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CustomerResponseDto>> findByDocumentType(HttpServletRequest servletRequest,
                                                                      @RequestParam(value = "documentType", required = false) String documentType) {
    logRequest.accept(servletRequest);
    List<CustomerResponseDto> customerResponseDtoList = service.findByDocumentType(documentType);
    return (customerResponseDtoList == null || customerResponseDtoList.isEmpty())
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(service.findByDocumentType(documentType));
  }

  @PostMapping
  public ResponseEntity<Void> save(HttpServletRequest servletRequest,
                                   @Valid @RequestBody CustomerRequestDto customerRequestDto) {
    logRequest.accept(servletRequest);
    Long uniqueCode = service.save(customerRequestDto);
    return ResponseEntity.created(buildUriLocation.apply(uniqueCode)).build();
  }

  @PutMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> update(HttpServletRequest servletRequest,
                                     @Valid @RequestBody CustomerRequestDto customerRequestDto,
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    Long updatedCustomerUniqueCode = service.update(uniqueCode, customerRequestDto);
    return ResponseEntity.created(buildUriLocation.apply(updatedCustomerUniqueCode)).build();
  }

  @DeleteMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> delete(HttpServletRequest servletRequest,
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    service.deleteByUniqueCode(uniqueCode);
    return ResponseEntity.noContent().location(buildUriLocation.apply(uniqueCode)).build();
  }

  private final static Consumer<HttpServletRequest> logRequest = servletRequest->
      log.info("{}", servletRequest.getMethod() + ": " + servletRequest.getRequestURI());

  private final static Function<Long, URI> buildUriLocation = uniqueCode ->
      ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{uniqueCode}")
      .buildAndExpand(uniqueCode)
      .toUri();
}
