package com.demo.ibk.business.customer.infrastructure.resource.rest;

import com.demo.ibk.business.customer.application.service.CustomerService;
import com.demo.ibk.business.customer.infrastructure.constant.SwaggerConstant;
import com.demo.ibk.business.customer.domain.model.request.CustomerRequest;
import com.demo.ibk.business.customer.domain.model.response.Customer;
import com.demo.ibk.business.customer.infrastructure.exception.model.ApiExceptionResponse;
import com.demo.ibk.business.customer.infrastructure.logstash.Markers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@ApiResponses(value = {
    @ApiResponse(
        responseCode = "400",
        description = "Bad request",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ApiExceptionResponse.class),
            examples = @ExampleObject(value = SwaggerConstant.ApiExceptionResponse_badRequest))),
    @ApiResponse(
        responseCode = "404",
        description = "Not found",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ApiExceptionResponse.class),
            examples = @ExampleObject(value = SwaggerConstant.ApiExceptionResponse_notFound))),
})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ibk/business/v1/customers")
public class CustomerRestService {

  private final CustomerService service;

  @Operation(summary = "Get a customer by unique customer code" , tags = "customers")
  @ApiResponse(responseCode = "200", description = "Customer obtained successfully")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{uniqueCode}")
  public ResponseEntity<Customer> findByUniqueCode(HttpServletRequest servletRequest,
                                                   @Parameter(description = "unique customer code", example = "1")
                                                   @PathVariable(name = "uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    return ResponseEntity.ok(service.findByUniqueCode(uniqueCode));
  }

  @Operation(summary = "Get a list of customers by document type. If the document type is not given, get a list of all customers", tags = "customers")
  @ApiResponse(responseCode = "200", description = "Successfully obtained customer's list ")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Customer>> findByDocumentType(HttpServletRequest servletRequest,
                                                           @Parameter(description = "identification document type", example = "76517368")
                                                           @RequestParam(value = "documentType", required = false) String documentType) {
    logRequest.accept(servletRequest);
    List<Customer> customerList = service.findByDocumentType(documentType);
    return (customerList == null || customerList.isEmpty())
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(service.findByDocumentType(documentType));
  }

  @Operation(summary = "Save a new customer in the system", tags = "customers")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping
  public ResponseEntity<Void> save(
      HttpServletRequest servletRequest,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = {@Content(
              schema =  @Schema(implementation = CustomerRequest.class),
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = {
                  @ExampleObject(value = SwaggerConstant.CustomerRequest_DNI, description = "Request with DNI", name = "DNI"),
                  @ExampleObject(value = SwaggerConstant.CustomerRequest_RUC, description = "Request with RUC", name = "RUC")
              })})
      @Valid @RequestBody CustomerRequest customerRequest) {
    logRequest.accept(servletRequest);
    Long uniqueCode = service.save(customerRequest);
    return ResponseEntity.created(buildUriLocation.apply(uniqueCode)).build();
  }

  @Operation(summary = "Update the customer's information", tags = "customers")
  @ApiResponse(responseCode = "201", description = "Created")
  @PutMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> update(HttpServletRequest servletRequest,
                                     @Valid @RequestBody CustomerRequest customerRequest,
                                     @Parameter(description = "unique customer code", example = "1")
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    Long updatedCustomerUniqueCode = service.update(uniqueCode, customerRequest);
    return ResponseEntity.created(buildUriLocation.apply(updatedCustomerUniqueCode)).build();
  }

  @Operation(summary = "Delete the customer's information of system", tags = "customers")
  @ApiResponse(responseCode = "204", description = "Not content")
  @DeleteMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> delete(HttpServletRequest servletRequest,
                                     @Parameter(description = "unique customer code", example = "1")
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    logRequest.accept(servletRequest);
    service.deleteByUniqueCode(uniqueCode);
    return ResponseEntity.noContent().location(buildUriLocation.apply(uniqueCode)).build();
  }

  private final static Consumer<HttpServletRequest> logRequest = servletRequest->
      log.info(Markers.SENSITIVE_JSON, "{}", servletRequest.getMethod() + ": " + servletRequest.getRequestURI());

  private final static Function<Long, URI> buildUriLocation = uniqueCode ->
      ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{uniqueCode}")
      .buildAndExpand(uniqueCode)
      .toUri();

}
