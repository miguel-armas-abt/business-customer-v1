package com.demo.ibk.business.customer.domain.constant;

public class CustomerRegex {

  private CustomerRegex() {}

  public static final String DOCUMENT_TYPE = "^(DNI|IMMIGRATION|RUC|PASSPORT)$";
  public static final String ANY_STRING = "^([.0-9a-zA-ZŸÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÒÓÔÕÖ×ØÙÚÛÜÝàáâãäåæçèéêëìíîïòóôõöùúûüýÿÑñáéíóúÁÉÍÓÚ´‘-]+\\s)*[.0-9a-zA-ZŸÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÒÓÔÕÖ×ØÙÚÛÜÝàáâãäåæçèéêëìíîïòóôõöùúûüýÿÑñáéíóúÁÉÍÓÚ‘´-]+$";

}
