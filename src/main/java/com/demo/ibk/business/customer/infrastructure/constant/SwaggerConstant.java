package com.demo.ibk.business.customer.infrastructure.constant;

public class SwaggerConstant {
  private SwaggerConstant() {}

  public static final String API_DESCRIPTION = "La API para Gestión de Información de Clientes permite a los usuarios crear, actualizar y eliminar perfiles de clientes. Los usuarios también pueden buscar perfiles de clientes por nombre, correo electrónico o número de teléfono. \nLa API permite a los usuarios agregar y eliminar notas sobre los clientes, lo que puede ayudar a mantener un registro de las interacciones y los detalles importantes de los clientes. Además, la API proporciona un historial de interacciones con los clientes, lo que puede ayudar a los usuarios a mantenerse al día con las necesidades de los clientes. \nPara garantizar la seguridad de la API, se requiere autenticación de dos factores y se utiliza cifrado de extremo a extremo para proteger la información personal del cliente.";
  public static final String ApiExceptionResponse_notFound = "{\"type\":\"/no-data\",\"title\":\"The customer does not exist\",\"errorCode\":\"ERROR1000\",\"details\":[]}";
  public static final String ApiExceptionResponse_badRequest = "{\"type\":\"/business-rules\",\"title\":\"The document type is not defined\",\"errorCode\":\"ERROR1000\",\"details\":[]}";
  public static final String CustomerRequest_DNI = "{\"uniqueCode\":1,\"names\":\"Miguel Rodrigo\",\"lastNames\":\"Armas Abt\",\"documentType\":\"DNI\",\"documentNumber\":76517368,\"active\":false}";
  public static final String CustomerRequest_RUC = "{\"uniqueCode\":2,\"names\":\"Jouse Yanpieer\",\"lastNames\":\"Romero Salazar\",\"documentType\":\"RUC\",\"documentNumber\":76517369,\"active\":true}";
}
