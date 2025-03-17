package com.demo.ibk.business.commons.logging.injector;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.WebRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadContextUtils {

  public static final String[] TRACE_HEADERS = {
    "traceId",
    "parentId",
    "traceParent"
  };

  public static final String METHOD = ".method";
  public static final String URI = ".uri";
  public static final String HEADERS = ".headers";
  public static final String BODY = ".body";
  public static final String STATUS = ".status";

  public static String getHeadersAsString(Map<String, String> headers) {
    return headers.entrySet().stream()
      .map(entry -> entry.getKey() + "=" + entry.getValue())
      .collect(Collectors.joining(", "));
  }

  public static Map<String, String> extractTraceFieldsFromHeaders(WebRequest request) {
    return extractTraceFields(request::getHeader);
  }

  public static Map<String, String> extractTraceFieldsFromHeaders(Map<String, String> headers) {
    return extractTraceFields(headers::get);
  }

  private static Map<String, String> extractTraceFields(UnaryOperator<String> headerProvider) {
    return Arrays.stream(TRACE_HEADERS)
      .map(traceField -> Map.entry(traceField, Optional.ofNullable(headerProvider.apply(traceField))))
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
