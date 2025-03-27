package com.demo.poc.commons.custom.properties.restclient;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RestClient {

  private String endpoint;
  private Map<String, String> headers;

}
