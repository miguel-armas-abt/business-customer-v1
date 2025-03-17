package com.demo.ibk.commons.logging.injector.utils;

import static com.demo.ibk.commons.logging.injector.constants.LoggingConstant.TRACE_HEADERS;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.WebRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderFilter {

  public static String getHeadersAsString(Map<String, String> headers) {
    return headers.entrySet().stream()
      .map(entry -> entry.getKey() + "=" + entry.getValue())
      .collect(Collectors.joining(", "));
  }

  public static Map<String, String> extractTraceHeaders(WebRequest request) {
    return extractHeaders(request::getHeader, TRACE_HEADERS);
  }

  public static Map<String, String> extractTraceHeaders(Map<String, String> headers) {
    return extractHeaders(headers::get, TRACE_HEADERS);
  }

  private static Map<String, String> extractHeaders(UnaryOperator<String> headerProvider, String[] headerNames) {
    return Arrays.stream(headerNames)
      .map(headerName -> Map.entry(headerName, Optional.ofNullable(headerProvider.apply(headerName))))
      .filter(entry -> entry.getValue().isPresent())
      .collect(Collectors.toMap(
        entry -> toCamelCase(entry.getKey()),
        entry -> entry.getValue().get()
      ));
  }

  private static String toCamelCase(String value) {
    String[] parts = value.split("-");
    return parts[0] + Arrays.stream(parts, 1, parts.length)
      .map(part -> part.substring(0, 1).toUpperCase() + part.substring(1))
      .collect(Collectors.joining());
  }
}
