package com.demo.ibk.business.customer.enums;

import com.demo.ibk.business.commons.errors.exceptions.InvalidDocumentTypeException;
import java.util.Arrays;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum DocumentType {

  DNI("DNI", "Documento Nacional de Identidad"),
  IMMIGRATION_CARD("IMMIGRATION", "Carné de extranjería"),
  RUC("RUC", "Registro Único de Contribuyentes"),
  PASSPORT("PASSPORT", "Pasaporte");

  private final String code;
  private final String description;

  public static final Consumer<String> validateDocumentType =
      documentType -> Arrays.stream(DocumentType.values())
          .filter(documentTypeCatalog -> documentTypeCatalog.getCode().equals(documentType))
          .findFirst()
          .orElseThrow(InvalidDocumentTypeException::new);

}
